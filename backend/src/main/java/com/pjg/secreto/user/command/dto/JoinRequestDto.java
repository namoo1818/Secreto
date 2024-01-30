package com.pjg.secreto.user.command.dto;

import com.pjg.secreto.user.common.entity.User;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class JoinRequestDto {
    private String email;
    private String password;
    private String name;

    public User convertUser(){
        return new User(this.email, this.password, this.name);
    }
}
