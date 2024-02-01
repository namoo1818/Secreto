package com.pjg.secreto.room.command.dto;

import lombok.Data;

import java.util.List;

@Data
public class InsertMatchingRequestDto {

    private Long roomNo;

    private List<Long> roomUserNos;
}
