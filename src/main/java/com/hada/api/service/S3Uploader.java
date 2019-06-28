package com.hada.api.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {
	private final AmazonS3 amazonS3Client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	@Value("${cloud.aws.s3.bucket.directory}")
	private String directory;
	
	@Value("${cloud.aws.region.static}")
	private String region;
	
	@Value("${cloud.aws.credentials.accessKey}")
	private String accessKey;
	
	@Value("${cloud.aws.credentials.secretKey}")
	private String secretKey;
	
	public String upload(MultipartFile multipartFile, String email) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
        return upload(uploadFile, email);
    }

    private String upload(File uploadFile, String email) throws IOException {
    	String fileExt = FilenameUtils.getExtension(uploadFile.getName());
    	
	    BufferedImage thumbnail = Thumbnails.of(uploadFile) 
			.size(50, 50)
			.asBufferedImage();

	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	    String formatDate = format.format(new Date());
	    
	    File outputfile = new File(email + "_" + formatDate + "." + fileExt);
	    ImageIO.write(thumbnail, fileExt, outputfile);
	    
        String fileName = directory + "/" + outputfile.getName();
        String uploadImageUrl = putS3(outputfile, fileName);
        
        removeNewFile(uploadFile);
        removeNewFile(outputfile);
        
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
    	amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
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

}
