package com.pjg.secreto.mission.query.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchSuddenMissionResponseDto {

    private String missionSubmitAt;

    private String content;
}
