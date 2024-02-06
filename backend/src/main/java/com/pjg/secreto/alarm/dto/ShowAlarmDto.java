package com.pjg.secreto.alarm.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShowAlarmDto {

    private Long userNo;
    private Long alarmNo;
    private Long roomNo;

}

