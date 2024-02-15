package com.pjg.secreto.user.query.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank(message = "이메일의 양식에 맞지 않습니다.")
    @Email(message = "이메일이 빈값으로 전달되었습니다.")
    private String email;

    @NotBlank(message = "비밀번호가 공백으로 전달되었습니다.")
    private String password;
}
