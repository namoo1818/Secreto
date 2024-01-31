package com.pjg.secreto.mission.query.service;

import com.pjg.secreto.mission.query.dto.*;

import java.util.List;

public interface MissionQueryService {
    List<SearchSystemMissionResponseDto> searchSystemMission();

    List<SearchSuddenMissionResponseDto> searchSuddenMission(Long roomNo);

    List<SearchMissionListResponseDto> searchMissionList(Long roomNo);

    List<SearchUserMissionListResponseDto> searchUserMissionList(Long roomNo);

    SearchMemoResponseDto searchMemo(SearchMemoRequestDto searchMemoRequestDto);
}
