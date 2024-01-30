package com.pjg.secreto.room.command.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateRoomResponseDto {

    private String entryCode;
}
