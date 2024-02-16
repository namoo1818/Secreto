package com.pjg.secreto.mission.command.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class AddSuddenMissionRequestDto {

    private Long roomNo;

    private String content;
}
