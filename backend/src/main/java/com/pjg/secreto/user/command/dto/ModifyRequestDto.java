package com.pjg.secreto.user.command.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import javax.annotation.Nullable;

@Data
public class ModifyRequestDto {
    @NotBlank(message = "닉네임이 정상적인 값이 아닙니다.")
    private String nickname;

    @Nullable
    private String profileUrl;
}
