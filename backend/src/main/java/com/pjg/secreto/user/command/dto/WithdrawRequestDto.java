package com.pjg.secreto.user.command.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class WithdrawRequestDto {
    @NotBlank(message = "비밀번호가 공백으로 전달되었습니다.")
    private String password;
}
