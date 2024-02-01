package com.pjg.secreto.common.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pjg.secreto.common.Util.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@Service
@Slf4j
@RequiredArgsConstructor
public class S3FileUpload {

    @Value("${cloud.aws.s3}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public UploadFile uploadFile(MultipartFile file, String dirName) throws IOException {
        File uploadFile = convert(file)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));

        String originalFilename = file.getOriginalFilename();
        String storedLink = upload(uploadFile, dirName);

        Long authenticatedUserId = AuthUtils.getAuthenticatedUserId();

        UploadFile target = UploadFile.builder()
                .userno(authenticatedUserId)
                .originalFile(originalFilename)
                .saveFile(storedLink)
                .build();

        return target;
    }

    public UploadFile uploadFile(MultipartFile file) throws IOException {
        return this.uploadFile(file, "image");
    }


    private String upload(File uploadFile, String dirName) {
        String s3FileName = UUID.randomUUID() + "-" + uploadFile.getName();
        String fileName = dirName + "/" + s3FileName;
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        }else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        log.info(file.toString());
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
