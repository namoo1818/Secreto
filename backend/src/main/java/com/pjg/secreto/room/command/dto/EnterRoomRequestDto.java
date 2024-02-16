package com.pjg.secreto.room.command.dto;

import lombok.Data;

@Data
public class EnterRoomRequestDto {

    private Long userNo;
    private String entryCode;
    private String nickname;

}
