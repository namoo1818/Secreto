package com.pjg.secreto.user.query.dto;

import lombok.Data;

@Data
public class ValidateCertRequestDto {
    private String userId;
    private String validateCode;
}
