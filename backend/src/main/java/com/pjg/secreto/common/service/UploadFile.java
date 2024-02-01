package com.pjg.secreto.common.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UploadFile {
    private long no;
    private long userno;
    private String originalFile;
    private String saveFile;
}
