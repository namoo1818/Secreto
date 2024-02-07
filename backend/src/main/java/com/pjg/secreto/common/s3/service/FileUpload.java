package com.pjg.secreto.common.s3.service;

import com.pjg.secreto.common.s3.entity.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileUpload {
    UploadFile uploadFile(MultipartFile file) throws IOException;
}
