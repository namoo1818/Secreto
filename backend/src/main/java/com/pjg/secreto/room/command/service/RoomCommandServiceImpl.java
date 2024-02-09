package com.pjg.secreto.room.command.service;

import com.pjg.secreto.alarm.common.entity.Alarm;
import com.pjg.secreto.alarm.dto.AlarmDataDto;
import com.pjg.secreto.alarm.repository.AlarmRepository;
import com.pjg.secreto.alarm.service.EmitterService;
import com.pjg.secreto.history.command.repository.MatchingCommandRepository;
import com.pjg.secreto.history.common.entity.Matching;
import com.pjg.secreto.mission.command.repository.MissionScheduleCommandRepository;
import com.pjg.secreto.mission.command.repository.RoomMissionCommandRepository;
import com.pjg.secreto.mission.common.entity.MissionSchedule;
import com.pjg.secreto.mission.common.entity.RoomMission;
import com.pjg.secreto.mission.common.exception.MissionException;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private final EmitterService emitterService;
    private final AlarmRepository alarmRepository;


    // 방 생성 api (user 개발 완료 시 개발 예정)
    @Override
    public CreateRoomResponseDto createRoom(CreateRoomRequestDto createRoomRequestDto) {

        log.info("방 생성 api");

        try {

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
                    .entryCode(newToken)
                    .roomEndAt(LocalDateTime.now().plusWeeks(1)).build();
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

        log.info("방 이름 변경 api");

        try {
            Room room = roomQueryRepository.findById(changeRoomNameRequestDto.getRoomNo()).orElseThrow(() -> new UserException("해당 유저가 없습니다."));

            room.changeName(changeRoomNameRequestDto.getRoomName());

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }

    }

    @Override
    public SetRoomResponseDto setRoom(SetRoomRequestDto setRoomRequestDto) {

        log.info("방 세팅 api");

        try {

            // 수락된 유저들 조회
            List<RoomUser> roomUsers = roomUserQueryRepository.findAllByRoomIdWhereNotStandby(setRoomRequestDto.getRoomNo());

            if(roomUsers.size() < 3) {
                throw new RoomException("참여 유저가 3명 이상일 때부터 시작할 수 있습니다.");
            }

            Room room = roomQueryRepository.findById(setRoomRequestDto.getRoomNo()).orElseThrow(() -> new RoomException("해당 방이 없습니다."));

            RoomUser findRoomUserNo = roomUserQueryRepository.findByUserNoAndRoomNo(setRoomRequestDto.getUserNo(), room.getId())
                    .orElseThrow(() -> new RoomException("해당 유저는 방에 속해있지 않습니다."));

            if(!Objects.equals(room.getHostNo(), findRoomUserNo.getId())) {
                throw new RoomException("해당 유저는 방장이 아닙니다.");
            }

            // 현재 날짜
            LocalDate today = LocalDateTime.now().toLocalDate();
            LocalTime t = LocalDateTime.now().toLocalTime();

            log.info("현재 날짜 : " + today);

            // 방 끝나는 일정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime roomEndDateTime = LocalDateTime.parse(setRoomRequestDto.getRoomEndAt(), formatter);
            log.info("방 끝나는 일정 : " + roomEndDateTime);

            // 미션이 처음으로 주어지는 날짜
            LocalDate missionStartDate = LocalDate.parse(setRoomRequestDto.getMissionStartAt(), DateTimeFormatter.ISO_DATE);
            log.info("미션이 처음으로 주어지는 날짜 : " + missionStartDate);

            // 미션이 주어지는 시간
            LocalTime missionSubmitTime = LocalTime.parse(setRoomRequestDto.getMissionSubmitTime());
            log.info("미션이 주어지는 시간 : " + missionSubmitTime);

            int period = setRoomRequestDto.getPeriod();

            Period diff = Period.between(missionStartDate, roomEndDateTime.toLocalDate());
            int totalDays = diff.getDays();
            log.info("시작일과 종료일의 날짜 차이 : " + totalDays);

            for(int i=0; i<totalDays; i+=period) {

                LocalDateTime missionStartDateTime = LocalDateTime.of(missionStartDate, missionSubmitTime);
                LocalDateTime date = missionStartDateTime.plusDays(i);
                MissionSchedule missionSchedule = MissionSchedule.builder().room(room).missionSubmitAt(date).build();
                missionScheduleCommandRepository.save(missionSchedule);
            }

            log.info("미션 스케쥴 저장 완료");

            // 방 미션에 미션 추가
            List<MissionDto> missionList = setRoomRequestDto.getMissionList();

            for(MissionDto mission : missionList) {
                RoomMission roomMission = RoomMission.builder().room(room).content(mission.getContent()).build();
                roomMissionCommandRepository.save(roomMission);
            }

            /**
             * 매칭 정보 추가를 위한 로직
             */
            // key 랜덤으로 섞기
            int keys[] = new int[roomUsers.size()];
            Random r = new Random();
            for(int i=0; i<roomUsers.size(); i++) {
                keys[i] = r.nextInt(roomUsers.size());

                for(int j=0; j<i; j++) {
                    if(keys[i] == keys[j]) {
                        i--;
                    }
                }
            }

            log.info("keys = " + Arrays.toString(keys));

            // 매칭 정보 저장

            for(int i=0; i<keys.length; i++) {

                RoomUser findRoomUser = roomUserQueryRepository.findById(roomUsers.get(keys[i]).getId())
                        .orElseThrow(() -> new RoomException("해당 유저가 존재하지 않습니다."));

                Matching matching = Matching.builder()
                        .roomUser(findRoomUser).matchingAt(LocalDateTime.now()).deprecatedAt(null)
                        .manitoNo(null).manitiNo(null).build();

                if(i == 0) {
                    matching.changeMatchingInfo(roomUsers.get(keys[keys.length-1]).getId(), roomUsers.get(keys[i+1]).getId());
                    findRoomUser.setMatchingInfo(roomUsers.get(keys[keys.length-1]).getId(), roomUsers.get(keys[i+1]).getId());
                }
                else if(i == keys.length-1) {
                    matching.changeMatchingInfo(roomUsers.get(keys[i-1]).getId(), roomUsers.get(keys[0]).getId());
                    findRoomUser.setMatchingInfo(roomUsers.get(keys[i-1]).getId(), roomUsers.get(keys[0]).getId());
                }
                else {
                    matching.changeMatchingInfo(roomUsers.get(keys[i-1]).getId(), roomUsers.get(keys[i+1]).getId());
                    findRoomUser.setMatchingInfo(roomUsers.get(keys[i-1]).getId(), roomUsers.get(keys[i+1]).getId());
                }

                matchingCommandRepository.save(matching);

            }

            for(RoomUser ru : roomUsers) {

                // 유저에게 알림 발송
                AlarmDataDto alarmDataDto = AlarmDataDto.builder()
                        .content("방이 시작되었습니다.")
                        .readYn(false)
                        .generatedAt(LocalDateTime.now())
                        .author(ru.getRoom().getRoomName() + " 방이 시작되었습니다.")
                        .roomUserNo(ru.getId()).build();

                emitterService.alarm(ru.getId(), alarmDataDto, "방이 시작되었습니다.", "start");

                Alarm alarm = Alarm.builder()
                        .author(alarmDataDto.getAuthor())
                        .content(alarmDataDto.getContent())
                        .readYn(alarmDataDto.getReadYn())
                        .generatedAt(alarmDataDto.getGeneratedAt())
                        .roomUser(ru).build();

                alarmRepository.save(alarm);
            }


            // 방 정보 수정
            room.startRoom(LocalDateTime.now(), roomEndDateTime,
                    setRoomRequestDto.getHostParticipantYn(), setRoomRequestDto.getCommonYn(),
                    missionSubmitTime, missionStartDate, true);

            SetRoomResponseDto result = SetRoomResponseDto.builder().roomNo(setRoomRequestDto.getRoomNo()).build();

            return result;

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }

    }

    @Override
    public Long enterRoom(EnterRoomRequestDto enterRoomRequestDto) {

        log.info("방 입장 api");

        try {

            Long userNo = enterRoomRequestDto.getUserNo();

            // 사용할 닉네임 입력
            Room findRoom = roomQueryRepository.findByEntryCode(enterRoomRequestDto.getEntryCode());
            User findUser = userQueryRepository.findById(userNo).orElseThrow(() -> new UserException("유저가 존재하지 않습니다."));

            List<RoomUser> roomUsers = roomUserQueryRepository.findAllByUserNoAndRoomNo(findUser.getId(), findRoom.getId());

            if(!roomUsers.isEmpty()) {
                throw new RoomException("해당 유저는 이미 방에 속해있습니다.");
            }
            else {

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
            }

            return findRoom.getId();

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public void exitRoom(ExitRoomRequestDto exitRoomRequestDto) {

        log.info("방 나가기 api");

        try {

            Room findRoom = roomQueryRepository.findById(exitRoomRequestDto.getRoomNo())
                    .orElseThrow(() -> new RoomException("해당 방이 존재하지 않습니다."));

            // 방 유저 조회
            RoomUser findRoomUser = roomUserQueryRepository.findByUserNoAndRoomNo(exitRoomRequestDto.getUserNo(),
                    exitRoomRequestDto.getRoomNo())
                    .orElseThrow(() -> new RoomException("해당 유저는 방에 속해있지 않습니다."));

            log.info("방 유저 식별키 : " + findRoomUser.getId());

            // 방 유저 정보 변경
            findRoomUser.leave();

            log.info("방 유저 정보 변경 완료");

            if(findRoom.getRoomStartYn() == null) {
                throw new RoomException("방 시작 여부가 null입니다. 데이터를 고쳐주세요.");
            }

            // 방이 시작한 상태이고 종료되지 않은 상태일 경우에만 마니또 마니띠 관계 변경
            if(findRoom.getRoomStartYn() && findRoom.getRoomEndAt().isAfter(LocalDateTime.now())) {

                // 나간 유저의 마니또와 마니띠 매칭
                RoomUser usersManito = roomUserQueryRepository.findById(findRoomUser.getUsersManito())
                        .orElseThrow(() -> new RoomException("해당 유저는 마니또가 없습니다."));

                log.info("manito 불러오기 완료");
                RoomUser usersManiti = roomUserQueryRepository.findById(findRoomUser.getUsersManiti())
                        .orElseThrow(() -> new RoomException("해당 유저는 마니띠가 없습니다."));

                log.info("manito 불러오기 완료");

                usersManito.setManiti(usersManiti.getId());
                usersManiti.setManito(usersManito.getId());


                Matching manitosMatching = Matching.builder()
                        .roomUser(usersManito)
                        .matchingAt(LocalDateTime.now())
                        .manitoNo(usersManito.getUsersManito())
                        .manitiNo(usersManito.getUsersManiti())
                        .build();

                Matching manitisMatching = Matching.builder()
                        .roomUser(usersManiti)
                        .matchingAt(LocalDateTime.now())
                        .manitoNo(usersManiti.getUsersManito())
                        .manitiNo(usersManiti.getUsersManiti())
                        .build();

                matchingCommandRepository.save(manitosMatching);
                matchingCommandRepository.save(manitisMatching);
            }

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public List<Long> acceptUser(AcceptUserRequestDto acceptUserRequestDto) {

        log.info("유저 수락 api");
        try {

            List<RoomUser> findRoomUsers = roomUserQueryRepository.findByRoomUserNos(acceptUserRequestDto.getRoomUserNos());

            List<Long> roomUserNos = new ArrayList<>();

            // 방 유저 정보 변경
            for(RoomUser ru : findRoomUsers) {
                ru.accepted();
                roomUserNos.add(ru.getId());


                // 유저에게 알림 발송
                AlarmDataDto alarmDataDto = AlarmDataDto.builder()
                        .content("방 입장이 수락되었습니다.")
                        .readYn(false)
                        .generatedAt(LocalDateTime.now())
                        .author(ru.getRoom().getRoomName() + " 방")
                        .roomUserNo(ru.getId()).build();

                emitterService.alarm(ru.getId(), alarmDataDto, "방 입장이 수락되었습니다.", "accept");

                Alarm alarm = Alarm.builder()
                        .author(alarmDataDto.getAuthor())
                        .content(alarmDataDto.getContent())
                        .readYn(alarmDataDto.getReadYn())
                        .generatedAt(alarmDataDto.getGeneratedAt())
                        .roomUser(ru).build();

                alarmRepository.save(alarm);
            }


            return roomUserNos;

        } catch (Exception e) {

            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public void denyUser(DenyUserRequestDto denyUserRequestDto) {

        log.info("유저 거절 api");
        try {
            roomUserCommandRepository.deleteAllByIds(denyUserRequestDto.getRoomUserNos());

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public void deligateAdmin(DeligateAdminRequestDto deligateAdminRequestDto) {

        log.info("방장 위임 api");
        try {

            Room findRoom = roomQueryRepository.findById(deligateAdminRequestDto.getRoomNo())
                    .orElseThrow(() -> new RoomException("해당 방이 존재하지 않습니다."));

            findRoom.changeHost(deligateAdminRequestDto.getNewHost());

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }

    }

    @Override
    public Boolean bookmarkRoom(BookmarkRoomRequestDto bookmarkRoomRequestDto) {

        log.info("즐겨찾기 api");

        try {

            Long userNo = bookmarkRoomRequestDto.getUserNo();

            RoomUser findRoomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, bookmarkRoomRequestDto.getRoomNo()).orElseThrow(() -> new MissionException("해당 방 유저가 없습니다."));

            return findRoomUser.bookmark();

        } catch (Exception e) {
            throw new RoomException(e.getMessage());
        }
    }

    @Override
    public void terminateRoom(TerminateRoomRequestDto terminateRoomRequestDto) {

        log.info("방 종료 api");

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

        log.info("모두 랜덤 매칭 api");

        try {

            /**
             * 매칭 정보 추가를 위한 로직
             */
            List<RoomUser> roomUsers = roomUserQueryRepository.findAllByRoomId(initMatchingRequestDto.getRoomNo());

            // key 랜덤으로 섞기
            int keys[] = new int[roomUsers.size()];
            Random r = new Random();
            for(int i=0; i<roomUsers.size(); i++) {
                keys[i] = r.nextInt(roomUsers.size()) + 1;

                for(int j=0; j<i; j++) {
                    if(keys[i] == keys[j]) {
                        i--;
                    }
                }
            }

            log.info("keys = " + Arrays.toString(keys));

            // 매칭 정보 저장
            for(int i=0; i<keys.length; i++) {

                RoomUser findRoomUser = roomUserQueryRepository.findById(roomUsers.get(keys[i]).getId())
                        .orElseThrow(() -> new RoomException("해당 유저가 존재하지 않습니다."));

                Matching matching = Matching.builder()
                        .roomUser(findRoomUser).matchingAt(LocalDateTime.now()).deprecatedAt(null)
                        .manitoNo(null).manitiNo(null).build();

                if(i == 0) {
                    matching.changeMatchingInfo(roomUsers.get(keys[keys.length-1]).getId(), roomUsers.get(keys[i+1]).getId());
                    findRoomUser.setMatchingInfo(roomUsers.get(keys[keys.length-1]).getId(), roomUsers.get(keys[i+1]).getId());
                }
                else if(i == keys.length-1) {
                    matching.changeMatchingInfo(roomUsers.get(keys[i-1]).getId(), roomUsers.get(keys[0]).getId());
                    findRoomUser.setMatchingInfo(roomUsers.get(keys[i-1]).getId(), roomUsers.get(keys[0]).getId());
                }
                else {
                    matching.changeMatchingInfo(roomUsers.get(keys[i-1]).getId(), roomUsers.get(keys[i+1]).getId());
                    findRoomUser.setMatchingInfo(roomUsers.get(keys[i-1]).getId(), roomUsers.get(keys[i+1]).getId());
                }

                matchingCommandRepository.save(matching);

            }

        } catch (Exception e) {

            throw new RoomException(e.getMessage());
        }

    }

    @Override
    public void insertMatching(InsertMatchingRequestDto insertMatchingRequestDto) {

        log.info("사이 매칭 api");

        try {

            // 방 유저 전체 리스트 조회
            List<RoomUser> findRoomUsers = roomUserQueryRepository.findAllByRoomNo(insertMatchingRequestDto.getRoomNo());

            RoomUser firstRoomUser = findRoomUsers.get(0);

            List<RoomUser> existsRoomUserList = new ArrayList<>();
            existsRoomUserList.add(firstRoomUser);

            RoomUser usersManiti = roomUserQueryRepository.findById(firstRoomUser.getUsersManiti())
                    .orElseThrow(() -> new RoomException("해당 유저는 존재하지 않습니다."));

            // 마니또 -> 자신 -> 마니띠로 정렬
            while(!Objects.equals(usersManiti.getUsersManiti(), firstRoomUser.getId())) {
                existsRoomUserList.add(usersManiti);
                usersManiti = roomUserQueryRepository.findById(usersManiti.getUsersManiti())
                        .orElseThrow(() -> new RoomException("해당 유저는 존재하지 않습니다."));
            }
            existsRoomUserList.add(usersManiti);

            // 방 입장이 수락된 유저 리스트 조회
            List<RoomUser> acceptedUserList = roomUserQueryRepository
                    .findAllByRoomUserNosAndRoomNo(insertMatchingRequestDto.getRoomUserNos(), insertMatchingRequestDto.getRoomNo());

            int totalRoomUserCnt = existsRoomUserList.size() + acceptedUserList.size();

            log.info("유저 수 : " + totalRoomUserCnt);
            // index 랜덤으로 섞기
            int indexs[] = new int[acceptedUserList.size()];
            Random r = new Random();
            for(int i=0; i<acceptedUserList.size(); i++) {
                indexs[i] = r.nextInt(totalRoomUserCnt);

                for(int j=0; j<i; j++) {
                    if(indexs[i] == indexs[j]) {
                        i--;
                    }
                }
            }

            Arrays.sort(indexs);
            log.info("indexs : " + Arrays.toString(indexs));

            List<RoomUser> newRelationList = new ArrayList<>();

            int existMemberIdx = 0;
            int newMemberIdx = 0;
            for(int i=0; i<totalRoomUserCnt; i++) {

                if(i == indexs[newMemberIdx]) {
                    log.info("newMemberIdx : " + newMemberIdx);
                    newRelationList.add(acceptedUserList.get(newMemberIdx));
                    if(newMemberIdx+1 != acceptedUserList.size()) {
                        newMemberIdx++;
                    }
                } else {
                    log.info("existMemberIdx : " + existMemberIdx);
                    newRelationList.add(existsRoomUserList.get(existMemberIdx));
                    if(existMemberIdx+1 != existsRoomUserList.size()) {
                        existMemberIdx++;
                    }
                }
            }

            // 매칭 정보 저장
            for(int i=0; i<newRelationList.size(); i++) {

                RoomUser findRoomUser = newRelationList.get(i);

                Matching matching = Matching.builder()
                        .roomUser(findRoomUser).matchingAt(LocalDateTime.now()).deprecatedAt(null)
                        .manitoNo(null).manitiNo(null).build();

                if(i == 0) {
                    matching.changeMatchingInfo(newRelationList.get(newRelationList.size()-1).getId(), newRelationList.get(i+1).getId());
                    findRoomUser.setMatchingInfo(newRelationList.get(newRelationList.size()-1).getId(), newRelationList.get(i+1).getId());
                }
                else if(i == newRelationList.size()-1) {
                    matching.changeMatchingInfo(newRelationList.get(i-1).getId(), newRelationList.get(0).getId());
                    findRoomUser.setMatchingInfo(newRelationList.get(i-1).getId(), newRelationList.get(0).getId());
                }
                else {
                    matching.changeMatchingInfo(newRelationList.get(i-1).getId(), newRelationList.get(i+1).getId());
                    findRoomUser.setMatchingInfo(newRelationList.get(i-1).getId(), newRelationList.get(i+1).getId());
                }

                matchingCommandRepository.save(matching);

            }

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

