package com.pjg.secreto.user.command.dto;

import lombok.Data;

@Data
public class ChangePasswordRequestDto {
    private String email;
    public String name;
}
