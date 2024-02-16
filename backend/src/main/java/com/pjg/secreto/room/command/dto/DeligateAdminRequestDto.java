package com.pjg.secreto.room.command.dto;

import lombok.Data;

@Data
public class DeligateAdminRequestDto {

    private Long roomNo;

    private Long newHost;
}
