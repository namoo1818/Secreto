package com.pjg.secreto.common.s3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UploadFile {
    private String originalFile;
    private String saveFile;
}
