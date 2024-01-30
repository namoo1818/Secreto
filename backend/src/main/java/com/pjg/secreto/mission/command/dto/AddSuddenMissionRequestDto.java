package com.pjg.secreto.mission.command.dto;

import lombok.Data;

@Data
public class AddSuddenMissionRequestDto {

    private Long roomNo;

    private String missionSubmitAt;

    private String content;
}
