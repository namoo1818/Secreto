package com.pjg.secreto.mission.command.dto;

import lombok.Data;

@Data
public class PredictManitoRequestDto {

    private Long roomNo;

    private Long expectedManito;
}
