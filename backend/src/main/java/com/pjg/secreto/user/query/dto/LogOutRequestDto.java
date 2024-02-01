package com.pjg.secreto.user.query.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogOutRequestDto {
    @NotBlank(message = "이메일의 양식에 맞지 않습니다.")
    @Email(message = "이메일이 빈값으로 전달되었습니다.")
    private String email;

    @NotBlank(message = "공급자의 값이 비정상적으로 전달되었습니다.")
    private String provider;
}
