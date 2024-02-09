package com.pjg.secreto.mission.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class SearchSuddenMissionResponseDto {

    private Long suddenMissionNo;

    private LocalDateTime missionSubmitAt;

    private String content;
}
