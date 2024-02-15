package com.pjg.secreto.mission.command.dto;

import lombok.Data;

@Data
public class UpdateMemoRequestDto {

    private Long userNo;
    private Long roomNo;
    private Long userMemoNo;
    private String memo;
    private String manitoPredictType;
    private Long memoTo;
}
