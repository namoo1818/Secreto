package com.pjg.secreto.user.command.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeLegacyPasswordRequestDto {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String old_password;

    @NotBlank
    private String new_password;
}
