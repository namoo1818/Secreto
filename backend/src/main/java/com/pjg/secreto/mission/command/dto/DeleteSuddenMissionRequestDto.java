package com.pjg.secreto.mission.command.dto;

import lombok.Data;

@Data
public class DeleteSuddenMissionRequestDto {

    private Long roomNo;

    private Long suddenMissionNo;
}
