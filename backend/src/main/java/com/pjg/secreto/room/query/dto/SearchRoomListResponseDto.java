package com.pjg.secreto.room.query.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchRoomListResponseDto {

    private Long roomNo;

    private String roomName;

    private String entryCode;

    private String roomStartAt;

    private String roomEndAt;

    private boolean hostParticipantYn;

    private boolean commonYn;

    private String missionSubmitTime;

    private String missionStartAt;

    private boolean roomStartYn;

    private boolean standbyYn;
}
