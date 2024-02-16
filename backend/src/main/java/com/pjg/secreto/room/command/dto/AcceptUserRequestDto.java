package com.pjg.secreto.room.command.dto;

import lombok.Data;

import java.util.List;

@Data
public class AcceptUserRequestDto {

    private List<Long> roomUserNos;
}
