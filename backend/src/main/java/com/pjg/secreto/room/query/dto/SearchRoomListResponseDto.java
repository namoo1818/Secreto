package com.pjg.secreto.room.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Data
public class SearchRoomListResponseDto {

    private Long roomNo;

    private String roomName;

    private String entryCode;

    private LocalDateTime roomStartAt;

    private LocalDateTime roomEndAt;

    private Boolean hostParticipantYn;

    private Boolean commonYn;

    private LocalTime missionSubmitTime;

    private LocalDate missionStartAt;

    private Boolean roomStartYn;

    private Boolean standbyYn;

    private String nickname;

    private int participantCnt;

    private Boolean bookmarkYn;

    private RoomStatus roomStatus;

    private Long hostUserNo;

}

