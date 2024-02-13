package com.pjg.secreto.room.query.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ShowMatchingLogsResponseDto {

    private Long matchingNo;
    private Long roomUserNo;
    private Long manitoNo;
    private Long manitiNo;
    private LocalDateTime matchingAt;

}
