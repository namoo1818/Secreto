package com.pjg.secreto.user.query.dto;

import lombok.Data;

@Data
public class LogOutRequestDto {
    private String email;
    private String provider;
}
