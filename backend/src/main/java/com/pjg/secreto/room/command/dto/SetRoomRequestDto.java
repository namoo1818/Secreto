package com.pjg.secreto.room.command.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class SetRoomRequestDto {

    private Long userNo;

    private Long roomNo;

//    private LocalDateTime roomEndAt;
    private String roomEndAt;

    private Boolean hostParticipantYn;

    private Boolean commonYn;

//    private LocalTime missionSubmitTime;
    private String missionSubmitTime;

//    private LocalDate missionStartAt;
    private String missionStartAt;

    private Integer period;

    private List<MissionDto> missionList;

}
