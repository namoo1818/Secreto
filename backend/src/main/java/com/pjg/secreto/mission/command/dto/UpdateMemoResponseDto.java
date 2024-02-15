package com.pjg.secreto.mission.command.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateMemoResponseDto {

    private Long userMemoNo;
}
