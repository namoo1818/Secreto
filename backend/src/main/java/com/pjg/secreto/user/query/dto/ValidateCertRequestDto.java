package com.pjg.secreto.user.query.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ValidateCertRequestDto {
    @NotBlank(message = "이메일의 양식에 맞지 않습니다.")
    @Email(message = "이메일이 빈값으로 전달되었습니다.")
    private String email;

    @NotEmpty(message = "검증코드가 빈 값으로 전달되었습니다.")
    private String validateCode;
}
