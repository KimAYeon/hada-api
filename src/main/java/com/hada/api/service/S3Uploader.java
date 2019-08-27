package com.hada.api.service;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.hada.api.exception.HadaApiErrorCode;
import com.hada.api.exception.HadaUploadException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Uploader {

	private static final Logger LOGGER = LogManager.getLogger(S3Uploader.class);

	private final AmazonS3 amazonS3Client;
	private String fileExt = "";

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Value("${cloud.aws.s3.bucket.directory}")
	private String directory;

	@Value("${cloud.aws.region.static}")
	private String region;

	@Value("${hada.api.profile.resize}")
	private int resize;

	public JSONObject upload(MultipartFile multipartFile, String email) throws IOException {
		fileExt = FilenameUtils.getExtension(multipartFile.getOriginalFilename());	// 파일 확장자
		
		byte[] imgBytes = multipartFile.getBytes();
		BufferedInputStream bufferedIS = new BufferedInputStream(new ByteArrayInputStream(imgBytes));
		int orientation = getOrientation(bufferedIS);

		ByteArrayInputStream byteIS = new ByteArrayInputStream(imgBytes);
		// 모바일 이미지 업로드 시, 가로/세로 모드에 따른 이미지 회전 문제 해결
		BufferedImage buffredI = rotateImageForMobile(byteIS, orientation);

//		File uploadFile = convert(multipartFile, email)
//				.orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 변환에 실패하였습니다."));
		
		String uploadUrl = upload(buffredI, email);
		
		String jsonStr = "{\"profile\":\""+uploadUrl+"\"}";
	
		JSONParser parser = new JSONParser();
		Object obj;
		JSONObject jsonObj = null;
		
		try {
			obj = parser.parse( jsonStr );
			jsonObj = (JSONObject) obj;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return jsonObj;
	}

	private String upload(BufferedImage uploadFile, String email) throws IOException {
		int thumbnail_width = resize;
		int thumbnail_height = resize;

		double imgWidth = uploadFile.getWidth();
		double imgHeight = uploadFile.getHeight();

		// 가로/세로 비율 유지
		if (imgWidth < imgHeight) {
			thumbnail_width = (int) ((imgWidth / imgHeight) * resize);
		} else {
			thumbnail_height = (int) ((imgHeight / imgWidth) * resize);
		}

		int imgType = (uploadFile.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;

//		BufferedImage thumbnail = new BufferedImage(thumbnail_width, thumbnail_height, imgType);
//		Graphics2D graphic = thumbnail.createGraphics();
//		graphic.drawImage(uploadFile, 0, 0, thumbnail_width, thumbnail_height, null);

	    BufferedImage thumbnail = Thumbnails.of(uploadFile) 
			.size(thumbnail_width, thumbnail_height)
			.asBufferedImage();

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		String formatDate = format.format(new Date());
	    File outputfile = new File(email + "_" + formatDate + "." + fileExt);

		ImageIO.write(thumbnail, fileExt, outputfile);

		String fileName = directory + "/" + outputfile.getName();
		String uploadImageUrl = putS3(outputfile, fileName);

		removeNewFile(outputfile);

		return uploadImageUrl;
	}

	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(
				new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			log.info(targetFile.getName() + " 파일을 삭제하였습니다.");
		} else {
			log.info(targetFile.getName() + "파일 삭제를 실패하였습니다.");
		}
	}

	// MultipartFile -> File 변환
	private Optional<File> convert(MultipartFile file, String email) throws IOException {
		String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		String formatDate = format.format(new Date());

		File convertFile = new File(email + "_" + formatDate + "." + fileExt);
		FileOutputStream fos = null;

		try {
			convertFile.createNewFile();
			fos = new FileOutputStream(convertFile);
			fos.write(file.getBytes());
		} finally {
			fos.close();
		}

		return Optional.of(convertFile);
	}

	public int getOrientation(BufferedInputStream is) throws IOException {
		int orientation = 1;
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(is);
			ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
			if(!ObjectUtils.isEmpty(directory)) {
				try {
					orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
				} catch (MetadataException me) {
					throw new HadaUploadException("이미지 각도를 가져올 수 없습니다.", HadaApiErrorCode.NOT_GET_ORIENTATION);
				}
			}
		} catch (ImageProcessingException e) {
			throw new HadaUploadException("이미지 업로드를 실패하였습니다..", HadaApiErrorCode.FAIL_UPLODAD_PROFILE);
		}
		return orientation;
	}

	public BufferedImage rotateImageForMobile(InputStream is, int orientation) throws IOException {
		BufferedImage bi = ImageIO.read(is);
		if (orientation == 6) { // 정위치
			return rotateImage(bi, 90);
		} else if (orientation == 1) { // 왼쪽으로 눕혔을때
			return bi;
		} else if (orientation == 3) { // 오른쪽으로 눕혔을때
			return rotateImage(bi, 180);
		} else if (orientation == 8) { // 180도
			return rotateImage(bi, 270);
		} else {
			return bi;
		}
	}

	public BufferedImage rotateImage(BufferedImage orgImage, int radians) {
		BufferedImage newImage;
		if (radians == 90 || radians == 270) {
			newImage = new BufferedImage(orgImage.getHeight(), orgImage.getWidth(), orgImage.getType());
		} else if (radians == 180) {
			newImage = new BufferedImage(orgImage.getWidth(), orgImage.getHeight(), orgImage.getType());
		} else {
			return orgImage;
		}
		Graphics2D graphics = (Graphics2D) newImage.getGraphics();
		graphics.rotate(Math.toRadians(radians), newImage.getWidth() / 2, newImage.getHeight() / 2);
		graphics.translate((newImage.getWidth() - orgImage.getWidth()) / 2,
				(newImage.getHeight() - orgImage.getHeight()) / 2);
		graphics.drawImage(orgImage, 0, 0, orgImage.getWidth(), orgImage.getHeight(), null);

		return newImage;
	}

}
