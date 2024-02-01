package com.pjg.secreto.user.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class WithdrawRequestDto {
    @NotBlank
    private String password;
}
