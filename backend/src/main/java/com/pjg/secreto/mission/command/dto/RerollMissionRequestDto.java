package com.pjg.secreto.mission.command.dto;

import lombok.Data;

@Data
public class RerollMissionRequestDto {

    private Long userNo;
    private Long roomNo;
    private Long userMissionNo;
}
