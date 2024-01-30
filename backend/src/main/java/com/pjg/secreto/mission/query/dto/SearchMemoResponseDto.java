package com.pjg.secreto.mission.query.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchMemoResponseDto {

    private Long userMemoNo;

    private String memo;

    private String manitoPredictType;

    private Long memoTo;
}
