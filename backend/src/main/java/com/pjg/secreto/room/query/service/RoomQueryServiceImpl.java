package com.pjg.secreto.room.query.service;

import com.pjg.secreto.room.common.entity.Room;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.common.exception.RoomException;
import com.pjg.secreto.room.query.dto.*;
import com.pjg.secreto.room.query.repository.RoomQueryRepository;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RoomQueryServiceImpl implements RoomQueryService{

    private final RoomQueryRepository roomQueryRepository;
    private final RoomUserQueryRepository roomUserQueryRepository;

    @Override
    public boolean enterRoom(CheckCodeDto checkCodeDto) {

        try {
            List<Room> rooms = roomQueryRepository.findAll();

            List<SearchEntryCodesDto> codeDto = rooms.stream().map(r -> new SearchEntryCodesDto(r.getEntryCode())).toList();

            boolean result = false;
            for (SearchEntryCodesDto ec : codeDto) {
                if(checkCodeDto.getEntryCode().equals(ec.getEntryCodes())) {
                    result = true;
                }
            }

            return result;
        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public List<SearchRoomUserListResponseDto> searchRoomUserList(Long roomNo) {

        try {

            Room findRoom = roomQueryRepository.findById(roomNo)
                    .orElseThrow(() -> new RoomException("해당 방이 없습니다."));

            List<RoomUser> roomUsers = roomUserQueryRepository.findAllByRoomId(roomNo);

            List<SearchRoomUserListResponseDto> result = roomUsers.stream()
                    .map(SearchRoomUserListResponseDto::of).toList();

            return result;

        } catch (Exception e) {

            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public List<SearchRoomListResponseDto> searchRoomList(Long userNo) {

        try {

            List<RoomUser> findRoomUsers = roomUserQueryRepository.findAllWithUserAndRoomByUserNo(userNo);

            List<SearchRoomListResponseDto> result = new ArrayList<>();
            for(RoomUser ru : findRoomUsers) {

                Long findRoomNo = ru.getId();
                RoomStatus roomStatus;
                if(ru.getStandbyYn()) {
                    roomStatus = RoomStatus.WAIT;
                }
                else {
                    roomStatus = RoomStatus.PARTICIPANT;
                }

                if(ru.getRoom().getRoomEndAt() != null) {
                    roomStatus = RoomStatus.END;
                }

                int findRoomUserCnt = roomUserQueryRepository.findParticipantCntByRoomNo(findRoomNo);

                result.add(SearchRoomListResponseDto.builder()
                        .roomNo(ru.getRoom().getId())
                        .roomName(ru.getRoom().getRoomName())
                        .entryCode(ru.getRoom().getEntryCode())
                        .roomStartAt(ru.getRoom().getRoomStartAt())
                        .roomEndAt(ru.getRoom().getRoomEndAt())
                        .hostParticipantYn(ru.getRoom().getHostParticipantYn())
                        .commonYn(ru.getRoom().getCommonYn())
                        .missionSubmitTime(ru.getRoom().getMissionSubmitTime())
                        .missionStartAt(ru.getRoom().getMissionStartAt())
                        .standbyYn(ru.getStandbyYn())
                        .nickname(ru.getNickname())
                        .participantCnt(findRoomUserCnt)
                        .bookmarkYn(ru.getBookmarkYn())
                        .roomStatus(roomStatus).build());

            }

            return result;
        } catch (Exception e) {

            throw new RoomException(e.getMessage());
        }

    }

    @Override
    public SearchRoomResponseDto searchRoom(Long userNo, Long roomNo) {

        try {

            RoomUser findRoomUser = roomUserQueryRepository.findWithUserAndRoomByUserNoAndRoomNo(userNo, roomNo).orElseThrow(() -> new RoomException("해당 방 유저가 없습니다."));

            RoomStatus roomStatus;
            if(findRoomUser.getStandbyYn()) {
                roomStatus = RoomStatus.WAIT;
            }
            else {
                roomStatus = RoomStatus.PARTICIPANT;
            }

            if(findRoomUser.getRoom().getRoomEndAt() != null) {
                roomStatus = RoomStatus.END;
            }

            SearchRoomResponseDto result = SearchRoomResponseDto.builder()
                    .roomNo(findRoomUser.getRoom().getId())
                    .roomName(findRoomUser.getRoom().getRoomName())
                    .entryCode(findRoomUser.getRoom().getEntryCode())
                    .roomStartAt(findRoomUser.getRoom().getRoomStartAt())
                    .roomEndAt(findRoomUser.getRoom().getRoomEndAt())
                    .hostParticipantYn(findRoomUser.getRoom().getHostParticipantYn())
                    .commonYn(findRoomUser.getRoom().getCommonYn())
                    .missionSubmitTime(findRoomUser.getRoom().getMissionSubmitTime())
                    .missionStartAt(findRoomUser.getRoom().getMissionStartAt())
                    .roomStartYn(findRoomUser.getRoom().getRoomStartYn())
                    .roomStatus(roomStatus)
                    .userInfo(new UserInfoDto(findRoomUser.getId(), findRoomUser.getNickname(), findRoomUser.getUser().getProfileUrl())).build();

            return result;

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }

    }

}


