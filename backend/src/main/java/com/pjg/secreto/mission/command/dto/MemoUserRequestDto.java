package com.pjg.secreto.mission.command.dto;

import com.pjg.secreto.history.common.entity.ManitoPredictType;
import lombok.Data;

@Data
public class MemoUserRequestDto {

    private Long userNo;
    private Long roomNo;
    private String memo;
    private ManitoPredictType manitoPredictType;
    private Long memoTo;
}
