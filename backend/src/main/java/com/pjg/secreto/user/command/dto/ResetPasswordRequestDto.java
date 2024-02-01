package com.pjg.secreto.user.command.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequestDto {
    @NotBlank(message = "비밀번호가 공백입니다.")
    private String password;
}
