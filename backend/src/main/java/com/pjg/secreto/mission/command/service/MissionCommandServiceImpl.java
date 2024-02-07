package com.pjg.secreto.mission.command.service;

import com.pjg.secreto.alarm.common.entity.Alarm;
import com.pjg.secreto.alarm.dto.AlarmDataDto;
import com.pjg.secreto.alarm.repository.AlarmRepository;
import com.pjg.secreto.alarm.service.AlarmService;
import com.pjg.secreto.alarm.service.EmitterService;
import com.pjg.secreto.history.command.repository.ManitoExpectLogCommandRepository;
import com.pjg.secreto.history.command.repository.UserMemoCommandRepository;
import com.pjg.secreto.history.common.entity.ManitoExpectLog;
import com.pjg.secreto.history.common.entity.UserMemo;
import com.pjg.secreto.history.query.repository.UserMemoQueryRepository;
import com.pjg.secreto.mission.command.dto.*;
import com.pjg.secreto.mission.command.repository.RoomMissionCommandRepository;
import com.pjg.secreto.mission.command.repository.SuddenMissionCommandRepository;
import com.pjg.secreto.mission.command.repository.UserMissionCommandRepository;
import com.pjg.secreto.mission.common.entity.*;
import com.pjg.secreto.mission.common.exception.MissionException;
import com.pjg.secreto.mission.query.repository.RoomMissionQueryRepository;
import com.pjg.secreto.mission.query.repository.SuddenMissionQueryRepository;
import com.pjg.secreto.room.common.entity.Room;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.query.repository.RoomQueryRepository;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class MissionCommandServiceImpl implements MissionCommandService {

    private final RoomQueryRepository roomQueryRepository;
    private final SuddenMissionCommandRepository suddenMissionCommandRepository;
    private final SuddenMissionQueryRepository suddenMissionQueryRepository;
    private final RoomUserQueryRepository roomUserQueryRepository;
    private final ManitoExpectLogCommandRepository manitoExpectLogCommandRepository;
    private final UserMemoCommandRepository userMemoCommandRepository;
    private final UserMemoQueryRepository userMemoQueryRepository;
    private final RoomMissionQueryRepository roomMissionQueryRepository;
    private final UserMissionCommandRepository userMissionCommandRepository;
    private final EmitterService emitterService;
    private final AlarmRepository alarmRepository;

    @Override
    public void addSuddenMission(AddSuddenMissionRequestDto addSuddenMissionRequestDto) {

        try {

            Room findRoom = roomQueryRepository.findById(addSuddenMissionRequestDto.getRoomNo())
                    .orElseThrow(() -> new MissionException("해당 방이 없습니다."));

            // 돌발 미션 저장 로직
            SuddenMission suddenMission = SuddenMission.builder().room(findRoom)
                    .missionSubmitAt(addSuddenMissionRequestDto.getMissionSubmitAt())
                    .content(addSuddenMissionRequestDto.getContent()).build();

            suddenMissionCommandRepository.save(suddenMission);

            // 유저 미션 생성 로직
            for(RoomUser ru : findRoom.getRoomUsers()) {

                UserMission userMission = UserMission.builder()
                        .missionReceivedAt(LocalDateTime.now())
                        .missionCertifyYn(false)
                        .missionType(MissionType.SUDDEN)
                        .missionRerollCount(0)
                        .roomUser(ru)
                        .content(suddenMission.getContent())
                        .roomMissionNo(suddenMission.getId()).build();

                userMissionCommandRepository.save(userMission);

                // 유저에게 알림 발송
                AlarmDataDto alarmDataDto = AlarmDataDto.builder()
                        .content(userMission.getContent())
                        .readYn(false)
                        .generatedAt(LocalDateTime.now())
                        .author("방장")
                        .roomUserNo(ru.getId()).build();

                emitterService.alarm(ru.getId(), alarmDataDto, "돌발 미션이 생성되었습니다.", "sudden");

                Alarm alarm = Alarm.builder()
                        .author(alarmDataDto.getAuthor())
                        .content(alarmDataDto.getContent())
                        .readYn(alarmDataDto.getReadYn())
                        .generatedAt(alarmDataDto.getGeneratedAt())
                        .roomUser(ru).build();

                alarmRepository.save(alarm);
            }



        } catch (Exception e) {

            throw new MissionException(e.getMessage());
        }
    }

    @Override
    public void deleteSuddenMission(DeleteSuddenMissionRequestDto deleteSuddenMissionRequestDto) {

        try {

            SuddenMission findSuddenMission = suddenMissionQueryRepository
                    .findByIdAndRoomNo(deleteSuddenMissionRequestDto.getSuddenMissionNo(),
                            deleteSuddenMissionRequestDto.getRoomNo());

            suddenMissionCommandRepository.delete(findSuddenMission);

        } catch (Exception e) {
            throw new MissionException(e.getMessage());
        }
    }

    @Override
    public void predictManito(PredictManitoRequestDto predictManitoRequestDto) {

        try {

            Long userNo = predictManitoRequestDto.getUserNo();

            RoomUser findRoomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, predictManitoRequestDto.getRoomNo()).orElseThrow(() -> new MissionException("해당 방 유저가 없습니다."));
            UserMemo userMemo = userMemoQueryRepository.findByMemoTo(predictManitoRequestDto.getExpectedManito())
                    .orElseGet(() -> {
                        return UserMemo.builder()
                                .memoTo(predictManitoRequestDto.getExpectedManito())
                                .memo("사유 없음")
                                .build();
            });

            ManitoExpectLog manitoExpectLog = ManitoExpectLog.builder()
                    .roomUser(findRoomUser)
                    .expectedUser(predictManitoRequestDto.getExpectedManito())
                    .expectedReason(userMemo.getMemo())
                    .expectedAt(LocalDateTime.now()).build();

            manitoExpectLogCommandRepository.save(manitoExpectLog);

        } catch (Exception e) {

            throw new MissionException(e.getMessage());
        }
    }

    @Override
    public MemoUserResponseDto memoUser(MemoUserRequestDto memoUserRequestDto) {

        try {

            Long userNo = memoUserRequestDto.getUserNo();

            RoomUser roomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, memoUserRequestDto.getRoomNo()).orElseThrow(() -> new MissionException("해당 방 유저가 없습니다."));

            UserMemo userMemo = UserMemo.builder()
                    .roomUser(roomUser).memo(memoUserRequestDto.getMemo())
                    .manitoPredictType(memoUserRequestDto.getManitoPredictType())
                    .memoTo(memoUserRequestDto.getMemoTo()).build();

            userMemoCommandRepository.save(userMemo);

            MemoUserResponseDto result = MemoUserResponseDto.builder().userMemoNo(userMemo.getId()).build();

            return result;

        } catch (Exception e) {

            throw new MissionException(e.getMessage());
        }
    }

    @Override
    public UpdateMemoResponseDto updateMemo(UpdateMemoRequestDto updateMemoRequestDto) {

        try {

            Long userNo = updateMemoRequestDto.getUserNo();

            RoomUser findRoomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, updateMemoRequestDto.getRoomNo()).orElseThrow(() -> new MissionException("해당 방 유저가 없습니다."));

            UserMemo findUserMemo = userMemoQueryRepository.findByRoomUserNo(findRoomUser.getId());

            UpdateMemoResponseDto result = UpdateMemoResponseDto.builder()
                    .userMemoNo(findUserMemo.getId()).build();

            return result;

        } catch (Exception e) {

            throw new MissionException(e.getMessage());
        }
    }

    // 정기 미션 날리기 기능, 방 끝나는 시각에 맞게 끝내는 기능 수행 메서드
//    @Scheduled(cron = "0 * 0-23 * * *") // 매일 정각마다 실행
//    public void throwRegularMission() {
//
//        /**
//         * 정기 미션 날리기 로직
//         */
//        LocalDateTime now = LocalDateTime.now();
//        log.info("현재 시각 : " + now);
//
//        // 미션 제출 일정이 현재 일자와 같은 모든 룸 유저들 조회
////        List<Room> findRooms = roomQueryRepository.findAllWithMissionScheduleAndRoomMission();
//        List<Room> findRoomsWithMissionSchedule = roomQueryRepository.findAllWithMissionSchedule();
//
//        List<Room> hasMissionRooms = new ArrayList<>();
//
//        for(Room r : findRoomsWithMissionSchedule) {
//
//            boolean hasMissionToday = false;
//            for(MissionSchedule ms : r.getMissionSchedules()) {
//
//                if(now.isEqual(ms.getMissionSubmitAt())) {
//                    hasMissionToday = true;
//                    break;
//                }
//            }
//
//            // 일자가 같을 경우 방 저장
//            if(hasMissionToday) {
//                hasMissionRooms.add(r);
//            }
//        }
//
//        log.info("일자 조회 완료");
//        List<Room> findRoomsWithRoomMissions = roomQueryRepository.findAllWithRoomMission(hasMissionRooms);
//
//        log.info("룸 미션 가지고있는 룸 조회 완료");
//        for(Room r : hasMissionRooms) {
//
//            List<RoomMission> roomMissions = roomMissionQueryRepository.findAllByRoomNO(r.getId());
//
//            List<RoomUser> findRoomUsers = roomUserQueryRepository.findAllByRoomNo(r.getId());
//
//            Collections.shuffle(roomMissions);
//
//            // 방의 유저들에게 미션 던지기 로직
//            for(RoomUser ru : findRoomUsers) {
//
//                // 개별 미션이면 미션 랜덤으로
//                if(!r.getCommonYn()) {
//                    Collections.shuffle(roomMissions);
//                }
//
//                // 유저 미션 생성
//                RoomMission roomMission = roomMissions.get(0);
//
//                UserMission userMission = UserMission.builder()
//                        .missionReceivedAt(LocalDateTime.now())
//                        .missionCertifyYn(false)
//                        .missionType(MissionType.REGULAR)
//                        .missionRerollCount(0)
//                        .roomUser(ru)
//                        .content(roomMission.getContent())
//                        .roomMissionNo(roomMission.getId()).build();
//
//                userMissionCommandRepository.save(userMission);
//
//                // 유저에게 알림 발송
//                AlarmDataDto alarmDataDto = AlarmDataDto.builder()
//                        .content(userMission.getContent())
//                        .readYn(false)
//                        .generatedAt(LocalDateTime.now())
//                        .author("시스템")
//                        .roomUserNo(ru.getId()).build();
//
//                emitterService.alarm(ru.getId(), alarmDataDto, "정기 미션이 생성되었습니다.", "regular");
//
//                Alarm alarm = Alarm.builder()
//                        .author(alarmDataDto.getAuthor())
//                        .content(alarmDataDto.getContent())
//                        .readYn(alarmDataDto.getReadYn())
//                        .generatedAt(alarmDataDto.getGeneratedAt())
//                        .roomUser(ru).build();
//
//                alarmRepository.save(alarm);
//            }
//
//        }
//
//        // 방 끝내는 로직
//        List<Room> rooms = roomQueryRepository.findAll();
//
//        for(Room r : rooms) {
//
//            if(r.getRoomEndAt().equals(now)) {
//                r.terminateRoom();
//            }
//        }
//
//    }
}


