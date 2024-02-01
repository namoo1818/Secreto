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
    @NotBlank(message = "이메일의 양식에 맞지 않습니다.")
    @Email(message = "이메일이 빈값으로 전달되었습니다.")
    private String email;

    @NotBlank(message = "비밀번호가 빈값으로 전달되었습니다.")
    private String password;
}
