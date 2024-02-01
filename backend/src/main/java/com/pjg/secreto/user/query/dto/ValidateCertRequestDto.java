package com.pjg.secreto.user.query.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ValidateCertRequestDto {
    @NotEmpty
    private String userId;

    @NotEmpty
    private String validateCode;
}
