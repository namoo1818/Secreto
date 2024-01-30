package com.pjg.secreto.mission.command.dto;

import lombok.Data;

@Data
public class MemoUserRequestDto {

    private Long roomNo;

    private String memo;

    private String manitoPredictType;

    private Long memoTo;
}
