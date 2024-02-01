package com.pjg.secreto.room.command.service;

import com.pjg.secreto.history.command.repository.MatchingCommandRepository;
import com.pjg.secreto.history.common.entity.Matching;
import com.pjg.secreto.mission.command.repository.MissionScheduleCommandRepository;
import com.pjg.secreto.mission.command.repository.RoomMissionCommandRepository;
import com.pjg.secreto.mission.common.entity.MissionSchedule;
import com.pjg.secreto.mission.common.entity.RoomMission;
import com.pjg.secreto.room.command.dto.*;
import com.pjg.secreto.room.command.repository.RoomCommandRepository;
import com.pjg.secreto.room.command.repository.RoomUserCommandRepository;
import com.pjg.secreto.room.common.entity.Room;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.common.exception.RoomException;
import com.pjg.secreto.room.query.dto.SearchEntryCodesDto;
import com.pjg.secreto.room.query.repository.RoomQueryRepository;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.common.exception.UserException;
import com.pjg.secreto.user.query.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RoomCommandServiceImpl implements RoomCommandService {

    private final RoomQueryRepository roomQueryRepository;
    private final RoomCommandRepository roomCommandRepository;
    private final RoomUserCommandRepository roomUserCommandRepository;
    private final MissionScheduleCommandRepository missionScheduleCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final RoomMissionCommandRepository roomMissionCommandRepository;
    private final RoomUserQueryRepository roomUserQueryRepository;
    private final MatchingCommandRepository matchingCommandRepository;


    // 방 생성 api (user 개발 완료 시 개발 예정)
    @Override
    public CreateRoomResponseDto createRoom(CreateRoomRequestDto createRoomRequestDto) {

        try {

            // 방 생성 유저 id 꺼내기 (security 세팅 완료 시 수정)
            Long userNo = createRoomRequestDto.getUserNo();

            List<Room> rooms = roomQueryRepository.findAll();

            List<SearchEntryCodesDto> codeDto = rooms.stream().map(r -> new SearchEntryCodesDto(r.getEntryCode())).toList();

            List<String> existCodes = new ArrayList<>();

            for (SearchEntryCodesDto ec : codeDto) {
                existCodes.add(ec.getEntryCodes());
            }

            // 모든 방의 입장 코드 조회 후 같은 코드가 있으면 다시 돌림
            boolean isExist = false;
            String newToken;
            do {
                // 입장 코드 생성
                newToken = generateRandomCode();
                for (String code : existCodes) {
                    if (newToken.equals(code)) {
                        log.info("중복된 코드");
                        isExist = true;
                        break;
                    }
                }

            } while (isExist);
            log.info("생성된 입장 코드 : " + newToken);


            // 방 생성
            Room room = Room.builder().roomName(createRoomRequestDto.getRoomName())
                    .entryCode(newToken).build();
            roomCommandRepository.save(room);

            log.info("방 식별키 : " + room.getId());

            // 방 별 유저에 방장 추가
            User user = userQueryRepository.findById(userNo).orElseThrow(() -> new UserException("해당 유저가 없습니다."));

            log.info("룸 유저 생성");
            RoomUser roomUser = RoomUser.builder().user(user).room(room).userEntryAt(LocalDateTime.now())
                    .userLeaveAt(null).standByYn(false).nickname(createRoomRequestDto.getHostNickname())
                    .bookmarkYn(false).build();
            roomUserCommandRepository.save(roomUser);

            log.info("룸 유저 식별키 : " + roomUser.getId());
            // room에 방장 id 추가
            room.changeHost(roomUser.getId());

            // 방 입장 코드 반환
            CreateRoomResponseDto result = CreateRoomResponseDto.builder().entryCode(newToken).build();

            return result;
        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public void changeRoomName(ChangeRoomNameRequestDto changeRoomNameRequestDto) {

        try {
            Room room = roomQueryRepository.findById(changeRoomNameRequestDto.getRoomNo()).orElseThrow(() -> new UserException("해당 유저가 없습니다."));

            room.changeName(changeRoomNameRequestDto.getRoomName());

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }

    }

    @Override
    public SetRoomResponseDto setRoom(SetRoomRequestDto setRoomRequestDto) {

        try {
            Room room = roomQueryRepository.findById(setRoomRequestDto.getRoomNo()).orElseThrow(() -> new RoomException("해당 방이 없습니다."));

            // 현재 날짜
            LocalDate today = LocalDateTime.now().toLocalDate();
            LocalTime t = LocalDateTime.now().toLocalTime();

            log.info("현재 날짜 : " + today);

            // 미션 일정 생성 (미션 시작일과 방 끝나는 날짜를 기준으로 주기마다 날짜 생성해야 함)
//            LocalDateTime startDT = LocalDateTime.of(2024, 1, 10, 18, 40, 25);
//            LocalDateTime endDT = LocalDateTime.of(2024, 2, 1, 14, 30, 55);
//            int period = 3;
//            LocalDate missionStartDate = startDT.toLocalDate();
//            LocalDate roomEndDate = endDT.toLocalDate();

            int period = setRoomRequestDto.getPeriod();
            LocalDate missionStartDate = setRoomRequestDto.getMissionStartAt();
            LocalDate roomEndDate = setRoomRequestDto.getRoomEndAt().toLocalDate();

            Period diff = Period.between(missionStartDate, roomEndDate);
            int totalDays = diff.getDays();
            log.info("시작일과 종료일의 날짜 차이 : " + totalDays);

            for(int i=0; i<totalDays; i+=period) {

                LocalDate date = missionStartDate.plusDays(i);
                MissionSchedule missionSchedule = MissionSchedule.builder().room(room).missionSubmitAt(date).build();
                missionScheduleCommandRepository.save(missionSchedule);
            }


            // 방 미션에 미션 추가
            List<MissionDto> missionList = setRoomRequestDto.getMissionList();

            for(MissionDto mission : missionList) {
                RoomMission roomMission = RoomMission.builder().room(room).content(mission.getContent()).build();
                roomMissionCommandRepository.save(roomMission);
            }

            /**
             * 매칭 정보 추가를 위한 로직
              */
            List<RoomUser> roomUsers = roomUserQueryRepository.findAllByRoomId(setRoomRequestDto.getRoomNo());

            List<Long> roomUserNos = new ArrayList<>(roomUsers.size());
            for(RoomUser ru : roomUsers) {
                roomUserNos.add(ru.getId());
            }

            // key 랜덤으로 섞기
            Random r = new Random();

            Long keys[] = new Long[roomUsers.size()];
            for(int i=0; i<roomUsers.size(); i++) {
                keys[i] = r.nextLong(roomUsers.size()) + 1;

                for(int j=0; j<i; j++) {
                    if(keys[i] == keys[j]) {
                        i--;
                    }
                }
            }

            log.info("keys = " + Arrays.toString(keys));

            // 매칭 정보 저장
            for(int i=0; i<keys.length; i++) {

                RoomUser findRoomUser = roomUserQueryRepository.findById(keys[i])
                        .orElseThrow(() -> new RoomException("해당 유저가 존재하지 않습니다."));

                Matching matching = Matching.builder()
                        .roomUser(findRoomUser).matchingAt(LocalDateTime.now()).deprecatedAt(null)
                        .manitoNo(null).manitiNo(null).build();
                if(i == 0) {
                    matching.changeMatchingInfo(keys[keys.length-1], keys[i+1]);
                    findRoomUser.setMatchingInfo(keys[keys.length-1], keys[i+1]);
                }
                else if(i == keys.length-1) {
                    matching.changeMatchingInfo(keys[i-1], keys[0]);
                    findRoomUser.setMatchingInfo(keys[i-1], keys[0]);
                }
                else {
                    matching.changeMatchingInfo(keys[i-1], keys[i+1]);
                    findRoomUser.setMatchingInfo(keys[i-1], keys[i+1]);
                }

                matchingCommandRepository.save(matching);

            }

            // 방 정보 수정
            room.startRoom(LocalDateTime.now(), setRoomRequestDto.getRoomEndAt(),
                    setRoomRequestDto.getHostParticipantYn(), setRoomRequestDto.getCommonYn(),
                    setRoomRequestDto.getMissionSubmitTime(), setRoomRequestDto.getMissionStartAt(), true);

            SetRoomResponseDto result = SetRoomResponseDto.builder().roomNo(setRoomRequestDto.getRoomNo()).build();
            return result;

        } catch (Exception e) {
            e.getStackTrace();
        }

        return null;

    }

    @Override
    public Long enterRoom(EnterRoomRequestDto enterRoomRequestDto) {

        try {

            // 방 생성 유저 id 꺼내기 (security 세팅 완료 시 수정)
            Long userNo = enterRoomRequestDto.getUserNo();

            // 사용할 닉네임 입력
            Room findRoom = roomQueryRepository.findByEntryCode(enterRoomRequestDto.getEntryCode());
            User findUser = userQueryRepository.findById(userNo).orElseThrow(() -> new UserException("유저가 존재하지 않습니다."));

            RoomUser roomUser = RoomUser.builder()
                    .nickname(enterRoomRequestDto.getNickname())
                    .room(findRoom)
                    .user(findUser)
                    .userEntryAt(null)
                    .userLeaveAt(null)
                    .standByYn(true)
                    .bookmarkYn(false)
                    .build();

            roomUserCommandRepository.save(roomUser);

            return findRoom.getId();

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public void exitRoom(ExitRoomRequestDto exitRoomRequestDto) {

        try {

            // 방 생성 유저 id 꺼내기 (security 세팅 완료 시 수정)
            Long userNo = exitRoomRequestDto.getUserNo();

            // 방 유저 조회
            Room findRoom = roomQueryRepository.findById(exitRoomRequestDto.getRoomNo())
                    .orElseThrow(() -> new RoomException("해당 방이 존재하지 않습니다."));
            User findUser = userQueryRepository.findById(userNo)
                    .orElseThrow(() -> new UserException("해당 유저가 존재하지 않습니다."));
            RoomUser roomUser = roomUserQueryRepository.findRoomUserByRoomAndUser(findRoom, findUser);
            log.info("방 유저 식별키 : " + roomUser);

            // 방 유저 정보 변경
            roomUser.leave();

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public void acceptUser(AcceptUserRequestDto acceptUserRequestDto) {

        try {

            List<RoomUser> findRoomUsers = roomUserQueryRepository.findByRoomUserNos(acceptUserRequestDto.getRoomUserNos());

            // 방 유저 정보 변경
            for(RoomUser ru : findRoomUsers) {
                ru.accepted();
            }

        } catch (Exception e) {

            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public void denyUser(DenyUserRequestDto denyUserRequestDto) {

        try {
            roomUserCommandRepository.deleteAllByIds(denyUserRequestDto.getRoomUserNos());

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public void deligateAdmin(DeligateAdminRequestDto deligateAdminRequestDto) {

        try {

            Room findRoom = roomQueryRepository.findById(deligateAdminRequestDto.getRoomNo())
                    .orElseThrow(() -> new RoomException("해당 방이 존재하지 않습니다."));

            findRoom.changeHost(deligateAdminRequestDto.getNewHost());

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }

    }

    @Override
    public void bookmarkRoom(BookmarkRoomRequestDto bookmarkRoomRequestDto) {

        try {

            Long userNo = bookmarkRoomRequestDto.getUserNo();

            RoomUser findRoomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, bookmarkRoomRequestDto.getRoomNo());

            findRoomUser.bookmark();

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public void terminateRoom(TerminateRoomRequestDto terminateRoomRequestDto) {

        try {

            Room findRoom = roomQueryRepository.findById(terminateRoomRequestDto.getRoomNo())
                    .orElseThrow(() -> new RoomException("방이 존재하지 않습니다."));

            findRoom.terminateRoom();

        } catch (Exception e) {

            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public void initMatching(InitMatchingRequestDto initMatchingRequestDto) {

        try {

            /**
             * 매칭 정보 추가를 위한 로직
             */
            List<RoomUser> roomUsers = roomUserQueryRepository.findAllByRoomId(initMatchingRequestDto.getRoomNo());

            List<Long> roomUserNos = new ArrayList<>(roomUsers.size());
            for(RoomUser ru : roomUsers) {
                roomUserNos.add(ru.getId());
            }

            // key 랜덤으로 섞기
            Random r = new Random();

            Long keys[] = new Long[roomUsers.size()];
            for(int i=0; i<roomUsers.size(); i++) {
                keys[i] = r.nextLong(roomUsers.size()) + 1;

                for(int j=0; j<i; j++) {
                    if(keys[i] == keys[j]) {
                        i--;
                    }
                }
            }

            log.info("keys = " + Arrays.toString(keys));

            // 매칭 정보 저장
            for(int i=0; i<keys.length; i++) {

                RoomUser findRoomUser = roomUserQueryRepository.findById(keys[i])
                        .orElseThrow(() -> new RoomException("해당 유저가 존재하지 않습니다."));

                Matching matching = Matching.builder()
                        .roomUser(findRoomUser).matchingAt(LocalDateTime.now()).deprecatedAt(null)
                        .manitoNo(null).manitiNo(null).build();
                if(i == 0) {
                    matching.changeMatchingInfo(keys[keys.length-1], keys[i+1]);
                    findRoomUser.setMatchingInfo(keys[keys.length-1], keys[i+1]);
                }
                else if(i == keys.length-1) {
                    matching.changeMatchingInfo(keys[i-1], keys[0]);
                    findRoomUser.setMatchingInfo(keys[i-1], keys[0]);
                }
                else {
                    matching.changeMatchingInfo(keys[i-1], keys[i+1]);
                    findRoomUser.setMatchingInfo(keys[i-1], keys[i+1]);
                }

                matchingCommandRepository.save(matching);

            }

        } catch (Exception e) {

            throw new RoomException(e.getMessage());
        }

    }

    @Override
    public void insertMatching(InsertMatchingRequestDto insertMatchingRequestDto) {

        try {

            List<RoomUser> newUsers = roomUserQueryRepository
                    .findAllByRoomUserNosAndRoomNo(insertMatchingRequestDto.getRoomUserNos(), insertMatchingRequestDto.getRoomNo());


//            List<Long> roomUserNos = new ArrayList<>(roomUsers.size());
//            for(RoomUser ru : roomUsers) {
//                roomUserNos.add(ru.getId());
//            }
//
//            // key 랜덤으로 섞기
//            Random r = new Random();
//
//            Long keys[] = new Long[roomUsers.size()];
//            for(int i=0; i<roomUsers.size(); i++) {
//                keys[i] = r.nextLong(roomUsers.size()) + 1;
//
//                for(int j=0; j<i; j++) {
//                    if(keys[i] == keys[j]) {
//                        i--;
//                    }
//                }
//            }

//            log.info("keys = " + Arrays.toString(keys));



        } catch (Exception e) {

            throw new RoomException(e.getMessage());
        }




    }


    /**
     * 방 입장 코드 생성 메서드
     */
    public String generateRandomCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        String generatedCode = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedCode;
    }

}
