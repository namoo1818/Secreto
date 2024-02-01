package com.pjg.secreto.user.command.dto;

import com.pjg.secreto.user.common.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Nullable;

@Data
public class JoinRequestDto {
    @NotBlank(message = "이메일의 양식에 맞지 않습니다.")
    @Email(message = "이메일이 빈값으로 전달되었습니다.")
    private String email;

    @NotBlank(message = "비밀번호가 공백으로 전달되었습니다.")
    private String password;

    @NotBlank(message = "닉네임이 공백으로 전달되었습니다.")
    private String nickname;

    @Nullable
    private String profileUrl;

    public User convertUser(){
        return new User(this.email, this.password, this.nickname);
    }
}
