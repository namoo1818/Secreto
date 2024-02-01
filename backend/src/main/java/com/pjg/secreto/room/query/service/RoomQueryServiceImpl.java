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

            List<RoomUser> findRoomUsers = roomUserQueryRepository.findAllWithRoomByUserNo(userNo);

            List<SearchRoomListResponseDto> result = findRoomUsers.stream()
                    .map(r -> new SearchRoomListResponseDto(
                            r.getRoom().getId(),
                            r.getRoom().getRoomName(),
                            r.getRoom().getEntryCode(),
                            r.getRoom().getRoomStartAt(),
                            r.getRoom().getRoomEndAt(),
                            r.getRoom().getHostParticipantYn(),
                            r.getRoom().getCommonYn(),
                            r.getRoom().getMissionSubmitTime(),
                            r.getRoom().getMissionStartAt(),
                            r.getRoom().getRoomStartYn(),
                            r.getStandbyYn())).toList();

            return result;
        } catch (Exception e) {

            throw new RoomException(e.getMessage());
        }

    }

    @Override
    public SearchRoomResponseDto searchRoom(Long roomNo) {

        try {

            Room find = roomQueryRepository.findById(roomNo).orElseThrow(() -> new RoomException("해당 방이 없습니다."));

            SearchRoomResponseDto result = SearchRoomResponseDto.builder()
                    .roomNo(find.getId()).roomName(find.getRoomName()).entryCode(find.getEntryCode())
                    .roomStartAt(find.getRoomStartAt()).roomEndAt(find.getRoomEndAt())
                    .hostParticipantYn(find.getHostParticipantYn()).commonYn(find.getCommonYn())
                    .missionSubmitTime(find.getMissionSubmitTime()).missionStartAt(find.getMissionStartAt())
                    .roomStartYn(find.getRoomStartYn()).build();

            return result;

        } catch (Exception e) {
            e.getStackTrace();
        }

        return null;
    }

}
