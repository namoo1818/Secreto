package com.pjg.secreto.alarm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class BoardAlarmDataDto {

    private Long alarmNo;

    private Long roomUserNo;

    private Long boardNo;

    private String author;

    private String content;

    private LocalDateTime generatedAt;

    private Boolean readYn;

}
