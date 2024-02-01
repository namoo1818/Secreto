package com.pjg.secreto.user.command.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePasswordRequestDto {

    @Email(message = "이메일 양식에 맞지 않습니다.")
    @NotBlank(message = "이메일이 빈값으로 전달되었습니다.")
    private String email;

    @NotBlank(message = "이름이 빈 값으로 존재하면 안됩니다.")
    public String name;
}
