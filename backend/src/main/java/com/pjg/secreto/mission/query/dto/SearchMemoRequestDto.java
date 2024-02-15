package com.pjg.secreto.mission.query.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchMemoRequestDto {

    private Long userNo;
    private Long roomNo;
    private Long memoTo;

}
