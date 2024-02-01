package com.pjg.secreto.common.service;

import com.pjg.secreto.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UploadController {
    private final S3FileUpload uploadService;

    @PostMapping("/upload/files")
    public ResponseEntity<?> upload(MultipartFile file) throws IOException {
        UploadFile result = uploadService.uploadFile(file);

        SuccessResponse response = SuccessResponse.builder()
                .result(result)
                .build();

        return ResponseEntity.ok(response);
    }

}
