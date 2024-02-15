package com.pjg.secreto.room.command.dto;

import lombok.Data;

@Data
public class ChangeRoomNameRequestDto {

    private String roomName;
    private Long roomNo;
}
