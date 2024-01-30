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

//        try {
//
//            Room findRoom = roomQueryRepository.findById(roomNo)
//                    .orElseThrow(() -> new RoomException("해당 방이 없습니다."));
//
//            List<SearchRoomUserListResponseDto> result = roomUserQueryRepository.findAllWithMatching();
//
//            for(SearchRoomUserListResponseDto rr : result) {
//                System.out.println("result = " + rr);
//            }
//
////            List<RoomUser> roomUsers = roomUserQueryRepository.findRoomUserByRoom(findRoom);
////
////            List<SearchRoomUserListResponseDto> roomUsers = roomUsers.stream()
////                    .map(ru -> new SearchRoomUserListResponseDto(ru.getRoomUserNO(),
////                            ru.getUserNo(),
////                            ru.getRoomNo(),
////                            ru.getUserRole(),
////                            ru.getUserEntryAt(),
////                            ru.getUserLeaveAt(),
////                            ru.getStandbyYn(),
////                            ru.getNickname()))
////                    .toList();
//
//
//            return null;
//        } catch (Exception e) {
//
//            throw new RoomException(e.getMessage());
//        }

        List<SearchRoomUserListResponseDto> resultList = new ArrayList<>();

        SearchRoomUserListResponseDto result1 = SearchRoomUserListResponseDto.builder()
                .roomUserNO(1L).userNo(4L).roomNo(2L)
                .userEntryAt(LocalDateTime.now()).userLeaveAt(LocalDateTime.now())
                .standbyYn(false).usersManito(2L).usersManiti(3L).nickname("상하악").build();

        SearchRoomUserListResponseDto result2 = SearchRoomUserListResponseDto.builder()
                .roomUserNO(1L).userNo(4L).roomNo(2L)
                .userEntryAt(LocalDateTime.now()).userLeaveAt(LocalDateTime.now())
                .standbyYn(false).usersManito(2L).usersManiti(3L).nickname("현차앙").build();

        SearchRoomUserListResponseDto result3 = SearchRoomUserListResponseDto.builder()
                .roomUserNO(1L).userNo(4L).roomNo(2L)
                .userEntryAt(LocalDateTime.now()).userLeaveAt(LocalDateTime.now())
                .standbyYn(false).usersManito(2L).usersManiti(3L).nickname("승우우").build();

        SearchRoomUserListResponseDto result4 = SearchRoomUserListResponseDto.builder()
                .roomUserNO(1L).userNo(4L).roomNo(2L)
                .userEntryAt(LocalDateTime.now()).userLeaveAt(LocalDateTime.now())
                .standbyYn(false).usersManito(2L).usersManiti(3L).nickname("인서엉").build();

        SearchRoomUserListResponseDto result5 = SearchRoomUserListResponseDto.builder()
                .roomUserNO(1L).userNo(4L).roomNo(2L)
                .userEntryAt(LocalDateTime.now()).userLeaveAt(LocalDateTime.now())
                .standbyYn(false).usersManito(2L).usersManiti(3L).nickname("시워언").build();

        SearchRoomUserListResponseDto result6 = SearchRoomUserListResponseDto.builder()
                .roomUserNO(1L).userNo(4L).roomNo(2L)
                .userEntryAt(LocalDateTime.now()).userLeaveAt(LocalDateTime.now())
                .standbyYn(false).usersManito(2L).usersManiti(3L).nickname("민지이").build();

        resultList.add(result1);
        resultList.add(result2);
        resultList.add(result3);
        resultList.add(result4);
        resultList.add(result5);
        resultList.add(result6);

        return resultList;


    }

    @Override
    public List<SearchRoomListResponseDto> searchRoomList() {

        List<SearchRoomListResponseDto> resultList = new ArrayList<>();

        SearchRoomListResponseDto result1 = SearchRoomListResponseDto.builder()
                .roomNo(1L).roomName("싸피 10기 방").entryCode("3DFSas")
                .roomStartAt("2024/01/15 00:00:00").roomEndAt("2024/01/22 00:00:00")
                .hostParticipantYn(true).commonYn(false).missionSubmitTime("12:00:00")
                .missionStartAt("2024/01/16").roomStartYn(false).standbyYn(false).build();

        SearchRoomListResponseDto result2 = SearchRoomListResponseDto.builder()
                .roomNo(1L).roomName("싸피 9기 방").entryCode("3DFSas")
                .roomStartAt("2024/01/15 00:00:00").roomEndAt("2024/01/22 00:00:00")
                .hostParticipantYn(true).commonYn(false).missionSubmitTime("12:00:00")
                .missionStartAt("2024/01/16").roomStartYn(false).standbyYn(false).build();

        SearchRoomListResponseDto result3 = SearchRoomListResponseDto.builder()
                .roomNo(1L).roomName("싸피 8기 방").entryCode("3DFSas")
                .roomStartAt("2024/01/15 00:00:00").roomEndAt("2024/01/22 00:00:00")
                .hostParticipantYn(true).commonYn(false).missionSubmitTime("12:00:00")
                .missionStartAt("2024/01/16").roomStartYn(false).standbyYn(false).build();

        resultList.add(result1);
        resultList.add(result2);
        resultList.add(result3);

        return resultList;
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
