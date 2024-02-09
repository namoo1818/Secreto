package com.pjg.secreto.mission.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
public class SearchMissionListResponseDto {

    private Long roomMissionNo;
    private String content;
}
