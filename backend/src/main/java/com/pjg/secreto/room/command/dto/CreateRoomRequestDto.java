package com.pjg.secreto.room.command.dto;

import lombok.Data;

@Data
public class CreateRoomRequestDto {

    private Long userNo;

    private String roomName;

    private String hostNickname;
}
