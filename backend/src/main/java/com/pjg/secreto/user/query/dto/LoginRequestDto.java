package com.pjg.secreto.user.query.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
