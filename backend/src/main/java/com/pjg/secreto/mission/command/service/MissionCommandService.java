package com.pjg.secreto.mission.command.service;

import com.pjg.secreto.mission.command.dto.*;

public interface MissionCommandService {
    void addSuddenMission(AddSuddenMissionRequestDto addSuddenMissionRequestDto);

    void deleteSuddenMission(DeleteSuddenMissionRequestDto deleteSuddenMissionRequestDto);

    void predictManito(PredictManitoRequestDto predictManitoRequestDto);

    MemoUserResponseDto memoUser(MemoUserRequestDto memoUserRequestDto);

    UpdateMemoResponseDto updateMemo(UpdateMemoRequestDto updateMemoRequestDto);
}
