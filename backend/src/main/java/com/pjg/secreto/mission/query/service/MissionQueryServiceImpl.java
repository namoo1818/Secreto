package com.pjg.secreto.mission.query.service;

import com.pjg.secreto.history.common.entity.UserMemo;
import com.pjg.secreto.history.query.repository.UserMemoQueryRepository;
import com.pjg.secreto.mission.common.entity.RoomMission;
import com.pjg.secreto.mission.common.entity.SuddenMission;
import com.pjg.secreto.mission.common.entity.SystemMission;
import com.pjg.secreto.mission.common.entity.UserMission;
import com.pjg.secreto.mission.common.exception.MissionException;
import com.pjg.secreto.mission.query.dto.*;
import com.pjg.secreto.mission.query.repository.RoomMissionQueryRepository;
import com.pjg.secreto.mission.query.repository.SuddenMissionQueryRepository;
import com.pjg.secreto.mission.query.repository.SystemMissionQueryRepository;
import com.pjg.secreto.mission.query.repository.UserMissionQueryRepository;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MissionQueryServiceImpl implements MissionQueryService {

    private final SystemMissionQueryRepository systemMissionQueryRepository;
    private final SuddenMissionQueryRepository suddenMissionQueryRepository;
    private final RoomMissionQueryRepository roomMissionQueryRepository;
    private final UserMissionQueryRepository userMissionQueryRepository;
    private final RoomUserQueryRepository roomUserQueryRepository;
    private final UserMemoQueryRepository userMemoQueryRepository;

    @Override
    public List<SearchSystemMissionResponseDto> searchSystemMission() {

        try {

            List<SystemMission> systemMissions = systemMissionQueryRepository.findAll();

            List<SearchSystemMissionResponseDto> result = systemMissions.stream()
                    .map(sm -> new SearchSystemMissionResponseDto(
                            sm.getContent())).toList();

            return result;

        } catch (Exception e) {

            throw new MissionException(e.getMessage());
        }
    }

    @Override
    public List<SearchSuddenMissionResponseDto> searchSuddenMission(Long roomNo) {

        try {

            List<SuddenMission> suddenMissions = suddenMissionQueryRepository.findAllByRoomNoAfterNow(roomNo);

            List<SearchSuddenMissionResponseDto> result = suddenMissions.stream()
                    .map(sm -> new SearchSuddenMissionResponseDto(
                            sm.getMissionSubmitAt(),
                            sm.getContent()
                    )).toList();

            return result;
        } catch (Exception e) {

            throw new MissionException(e.getMessage());
        }
    }

    @Override
    public List<SearchMissionListResponseDto> searchMissionList(Long roomNo) {

        try {

            List<RoomMission> roomMissions = roomMissionQueryRepository.findAllByRoomNO(roomNo);

            List<SearchMissionListResponseDto> result = roomMissions.stream()
                    .map(rm -> new SearchMissionListResponseDto(
                            rm.getContent()
                    )).toList();

            return result;

        } catch (Exception e) {

            throw new MissionException(e.getMessage());
        }
    }

    @Override
    public List<SearchUserMissionListResponseDto> searchUserMissionList(Long roomNo) {

        try {

            Long userNo = 1L;

            RoomUser findRoomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, roomNo);

            List<UserMission> userMissions = userMissionQueryRepository.findByRoomUserNo(findRoomUser.getId());

            List<SearchUserMissionListResponseDto> result = userMissions.stream()
                    .map(um -> SearchUserMissionListResponseDto.builder()
                            .missionReceivedAt(um.getMissionReceivedAt())
                            .content(um.getContent())
                            .missionType(um.getMissionType())
                            .missionCertifyYn(um.getMissionCertifyYn())
                            .missionRerollCount(um.getMissionRerollCount()).build()).toList();

            return result;

        } catch (Exception e) {

            throw new MissionException(e.getMessage());
        }

    }

    @Override
    public SearchMemoResponseDto searchMemo(SearchMemoRequestDto searchMemoRequestDto) {

        try {

            Long userNo = 1L;

            RoomUser findRoomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, searchMemoRequestDto.getRoomNo());
            UserMemo findUserMemo = userMemoQueryRepository.findByRoomUserNo(findRoomUser.getId());

            SearchMemoResponseDto result = SearchMemoResponseDto.builder()
                    .memo(findUserMemo.getMemo())
                    .userMemoNo(findUserMemo.getId())
                    .manitoPredictType(findUserMemo.getManitoPredictType())
                    .memoTo(findUserMemo.getMemoTo()).build();

            return result;

        } catch (Exception e) {

            throw new MissionException(e.getMessage());
        }

    }
}