package com.pjg.secreto.room.command.dto;

import lombok.Data;

@Data
public class ExitRoomRequestDto {

    private Long userNo;
    private Long roomNo;
}
