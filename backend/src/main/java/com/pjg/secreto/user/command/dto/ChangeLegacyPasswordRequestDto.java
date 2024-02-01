package com.pjg.secreto.user.command.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeLegacyPasswordRequestDto {

    @Email(message = "이메일의 형식에 맞지 않습니다.")
    @NotBlank
    private String email;

    @NotBlank(message = "전에 사용하던 비밀번호가 빈 값으로 전달되었습니다.")
    private String old_password;

    @NotBlank(message = "새로 사용할 비밀번호가 빈 값으로 전달되었습니다.")
    private String new_password;
}
