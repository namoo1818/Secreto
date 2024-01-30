package com.pjg.secreto.mission.query.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchUserMissionListResponseDto {

    private String content;

    private String missionReceivedAt;

    private String missionType;

    private int missionRerollCount;

    private boolean missionCertifyYn;

}
