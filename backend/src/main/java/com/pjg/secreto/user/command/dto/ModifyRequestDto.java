package com.pjg.secreto.user.command.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import javax.annotation.Nullable;

@Data
public class ModifyRequestDto {
    @NotBlank
    @Email
    private String email;


    @NotBlank
    private String password;

    @Nullable
    private String profileUrl;
}
