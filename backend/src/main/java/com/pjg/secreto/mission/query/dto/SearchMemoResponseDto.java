package com.pjg.secreto.mission.query.dto;

import com.pjg.secreto.history.common.entity.ManitoPredictType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchMemoResponseDto {

    private Long userMemoNo;

    private String memo;

    private ManitoPredictType manitoPredictType;

    private Long memoTo;
}
