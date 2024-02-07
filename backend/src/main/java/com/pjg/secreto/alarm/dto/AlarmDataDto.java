package com.pjg.secreto.alarm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class AlarmDataDto {

    private Long alarmNo;

    private Long roomUserNo;

    private String author;

    private String content;

    private LocalDateTime generatedAt;

    private Boolean readYn;
}
