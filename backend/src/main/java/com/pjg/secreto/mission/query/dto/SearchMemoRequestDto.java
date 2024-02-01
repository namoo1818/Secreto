package com.pjg.secreto.mission.query.dto;

import lombok.Data;

@Data
public class SearchMemoRequestDto {

    private Long userNo;
    private Long userMemoNo;
    private Long roomNo;

}
