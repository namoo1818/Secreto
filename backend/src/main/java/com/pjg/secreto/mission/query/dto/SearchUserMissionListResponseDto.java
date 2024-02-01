package com.pjg.secreto.mission.query.dto;

import com.pjg.secreto.mission.common.entity.MissionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class SearchUserMissionListResponseDto {

    private String content;

    private String missionReceivedAt;

    private MissionType missionType;

    private int missionRerollCount;

    private boolean missionCertifyYn;

}
