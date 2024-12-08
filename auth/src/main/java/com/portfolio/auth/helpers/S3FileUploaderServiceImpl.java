package com.portfolio.auth.helpers;


import com.portfolio.auth.common.exception.FileUploadFailedException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Service
@ConditionalOnProperty(name = "uploadServiceType", havingValue = "s3")
public class S3FileUploaderServiceImpl implements FileUploaderService {

    @Value("${s3.accessKey}")
    private String accessKey;
    @Value("${s3.region}")
    private String region;
    @Value("${s3.secretKey}")
    private String secretKey;
    @Value("${s3.bucketName}")
    private String bucketName;

    private S3Client s3client;

    @PostConstruct
    private void initializeAmazon() {
        AwsCredentials credentials =  AwsBasicCredentials.create(accessKey, secretKey);
        this.s3client =  S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Override
    public File upload(MultipartFile document, String path) throws FileUploadFailedException {
        try {
            String fileName = getFileName(document, path);
            File file = convertToFile(document, fileName);
            uploadToS3(fileName, file);
            return file;
        } catch (IOException e) {
            throw new FileUploadFailedException(e.getMessage());
        }
    }

    private void uploadToS3(String fileName, File file) {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build();
        try {
            s3client.putObject(putRequest, Paths.get(file.getPath()));
        } catch (Exception e) {
            System.err.println("Error uploading image: " + e.getMessage());
        }
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File file = new File(fileName);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }
}
