package com.pjg.secreto.user.command.dto;

import lombok.Data;

@Data
public class ModifyRequestDto {
    private String email;
    private String password;
    private String profileUrl;
}
