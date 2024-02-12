package com.pjg.secreto.mission.command.service;

import com.pjg.secreto.mission.command.dto.*;

public interface MissionCommandService {
    void addSuddenMission(AddSuddenMissionRequestDto addSuddenMissionRequestDto);

    void deleteSuddenMission(DeleteSuddenMissionRequestDto deleteSuddenMissionRequestDto);

    MemoUserResponseDto memoUser(MemoUserRequestDto memoUserRequestDto);

    void rerollMission(RerollMissionRequestDto rerollMissionRequestDto);
}

