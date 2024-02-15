package com.pjg.secreto.mission.query.dto;

import com.pjg.secreto.mission.common.entity.MissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
public class SearchUserMissionListResponseDto {

    private Long userMissionNo;

    private String content;

    private LocalDateTime missionReceivedAt;

    private MissionType missionType;

    private int missionRerollCount;

    private boolean missionCertifyYn;

}
