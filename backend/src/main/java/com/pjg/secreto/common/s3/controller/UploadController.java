package com.pjg.secreto.common.s3.controller;

import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.common.s3.entity.UploadFile;
import com.pjg.secreto.common.s3.service.FileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {
    private final FileUpload uploadService;

    @PostMapping("")
    public ResponseEntity<?> upload(MultipartFile file) throws IOException {
        UploadFile uploadFile = uploadService.uploadFile(file);

        SuccessResponse response = SuccessResponse.builder()
                .message("성공적으로 파일이 업로드 되었습니다.")
                .status(HttpStatus.OK)
                .result(uploadFile)
                .build();

        return ResponseEntity.ok(response);
    }

}
