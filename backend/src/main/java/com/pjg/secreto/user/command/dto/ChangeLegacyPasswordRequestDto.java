package com.pjg.secreto.user.command.dto;

import lombok.Data;

@Data
public class ChangeLegacyPasswordRequestDto {
    private String old_password;
    private String new_password;
}
