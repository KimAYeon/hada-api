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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Uploader {
	
	private final AmazonS3 amazonS3Client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	@Value("${cloud.aws.s3.bucket.directory}")
	private String directory;
	
	@Value("${cloud.aws.region.static}")
	private String region;
	
	public String upload(MultipartFile multipartFile, String email) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File�� ��ȯ�� �����߽��ϴ�."));
        return upload(uploadFile, email);
    }

    private String upload(File uploadFile, String email) throws IOException {
    	String fileExt = FilenameUtils.getExtension(uploadFile.getName());
    	
	    BufferedImage thumbnail = Thumbnails.of(uploadFile) 
			.size(128, 128)
			.asBufferedImage();

	    SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
	    String formatDate = format.format(new Date());
	    
	    File outputfile = new File("/root/app/git/" + email + "_" + formatDate + "." + fileExt);
	    ImageIO.write(thumbnail, fileExt, outputfile);
	    
        String fileName = directory + "/" + outputfile.getName();
        String uploadImageUrl = putS3(outputfile, fileName);
        
        removeNewFile(uploadFile);
        removeNewFile(outputfile);
        
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
    	amazonS3Client.putObject(new PutObjectRequest(bucket, uploadFile.getPath(), uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("������ �����Ǿ����ϴ�.");
        } else {
            log.info("������ �������� ���߽��ϴ�.");
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
