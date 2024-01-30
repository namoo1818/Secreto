package com.pjg.secreto.mission.command.service;

import com.pjg.secreto.mission.command.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MissionCommandServiceImpl implements MissionCommandService {

    @Override
    public void addSuddenMission(AddSuddenMissionRequestDto addSuddenMissionRequestDto) {

    }

    @Override
    public void deleteSuddenMission(DeleteSuddenMissionRequestDto deleteSuddenMissionRequestDto) {

    }

    @Override
    public void predictManito(PredictManitoRequestDto predictManitoRequestDto) {

    }

    @Override
    public MemoUserResponseDto memoUser(MemoUserRequestDto memoUserRequestDto) {

        MemoUserResponseDto result = MemoUserResponseDto.builder().userMemoNo(1L).build();

        return result;
    }

    @Override
    public UpdateMemoResponseDto updateMemo(UpdateMemoRequestDto updateMemoRequestDto) {

        UpdateMemoResponseDto result = UpdateMemoResponseDto.builder().userMemoNo(1L).build();

        return result;
    }
}
