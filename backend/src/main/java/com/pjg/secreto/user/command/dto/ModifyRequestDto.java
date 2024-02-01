package com.pjg.secreto.user.command.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import javax.annotation.Nullable;

@Data
public class ModifyRequestDto {
    @NotBlank(message = "이메일의 양식에 맞지 않습니다.")
    @Email(message = "이메일이 빈값으로 전달되었습니다.")
    private String email;


    @NotBlank(message = "비밀번호가 빈 값으로 전달되었습니다.")
    private String password;

    @Nullable
    private String profileUrl;
}
