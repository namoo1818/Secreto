package com.pjg.secreto.common.s3.service;

import com.pjg.secreto.common.s3.entity.UploadFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Component
@Slf4j
@RequiredArgsConstructor
public class S3FileUpload implements FileUpload {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    private final S3Client s3Client;

    @Override
    public UploadFile uploadFile(MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            log.info("file is null");
            throw new RuntimeException("파일이 없습니다.");
        }

        String originalFile = getFileName(multipartFile);
        String uploadedFile = uploadToS3(multipartFile);

        UploadFile result = UploadFile.builder()
                .originalFile(originalFile)
                .saveFile(uploadedFile)
                .build();

        return result;
    }

    private String uploadToS3(MultipartFile multipartFile) {
        String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .contentType(multipartFile.getContentType())
                    .contentLength(multipartFile.getSize())
                    .key(s3FileName)
                    .build();
            RequestBody requestBody = RequestBody.fromBytes(multipartFile.getBytes());
            s3Client.putObject(putObjectRequest, requestBody);
        } catch (IOException e) {
            log.error("cannot upload image", e);
            throw new RuntimeException("파일을 업로드 할 수 없습니다.");
        }

        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(bucket)
                .key(s3FileName)
                .build();

        return s3Client.utilities().getUrl(getUrlRequest).toString();
    }

    private String getFileName(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename();
    }
}
