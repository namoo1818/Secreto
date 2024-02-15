package com.pjg.secreto.user.command.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequestDto {
    @NotBlank(message = "검증코드가 없습니다.")
    private String validationCode;

    @NotBlank(message = "비밀번호가 빈값으로 전달되었습니다.")
    private String password;
}
