package com.pjg.secreto.alarm.service;

import com.pjg.secreto.alarm.common.entity.Alarm;
import com.pjg.secreto.alarm.common.exception.AlarmException;
import com.pjg.secreto.alarm.dto.AlarmDataDto;
import com.pjg.secreto.alarm.dto.BoardAlarmDataDto;
import com.pjg.secreto.alarm.repository.AlarmRepository;
import com.pjg.secreto.alarm.repository.EmitterRepository;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.common.exception.RoomException;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmitterService {

    private final Map<String, SseEmitter> emitterMap = new ConcurrentHashMap<>();
    private final RoomUserQueryRepository roomUserQueryRepository;
    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;

    private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60 * 60;
    private static final long RECONNECTION_TIMEOUT = 1000L;

    public SseEmitter subscribe(Long roomUserNo) {

        SseEmitter emitter = createEmitter(roomUserNo);

        sendToClient(roomUserNo, "roomUserNo : " + roomUserNo + "과의 알람 연결 성공", "sse 접속 성공");

        return emitter;

    }

    public void alarmWithNoStore(Long userId, AlarmDataDto alarmDataDto, String comment, String type) {
        try {
            sendToClient(userId, alarmDataDto, comment, type);

        } catch (Exception e) {
            throw new AlarmException(e.getMessage());
        }

    }

    public void alarm(Long userId, AlarmDataDto alarmDataDto, String comment, String type) {
        try {
            sendToClient(userId, alarmDataDto, comment, type);

            RoomUser findRoomUser = roomUserQueryRepository.findById(alarmDataDto.getRoomUserNo())
                    .orElseThrow(() -> new AlarmException("해당 유저가 존재하지 않습니다."));

            Alarm alarm = Alarm.builder()
                    .generatedAt(LocalDateTime.now())
                    .author(alarmDataDto.getAuthor())
                    .readYn(false)
                    .content(alarmDataDto.getContent())
                    .roomUser(findRoomUser).build();

            alarmRepository.save(alarm);

        } catch (Exception e) {
            throw new AlarmException(e.getMessage());
        }

    }

//    public <T> void broadcast(T data) {
//        emitterMap.forEach((id, emitter) -> {
//            try {
//                emitter.send(SseEmitter.event()
//                        .name("broadcast event")
//                        .id("broadcast event 1")
//                        .reconnectTime(RECONNECTION_TIMEOUT)
//                        .data(data, MediaType.APPLICATION_JSON));
//                log.info("sended notification, id={}, payload={}", id, data);
//            } catch (IOException e) {
//                //SSE 세션이 이미 해제된 경우
//                log.error("fail to send emitter id={}, {}", id, e.getMessage());
//            }
//        });
//    }

    private void sendToClient(Long roomUserNo, Object data, String comment) {

        SseEmitter emitter = emitterRepository.get(roomUserNo);
        if(emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(roomUserNo))
                        .name("sse")
                        .reconnectTime(RECONNECTION_TIMEOUT)
                        .data(data)
                        .comment(comment));

            } catch (IOException e) {
                emitterRepository.deleteById(roomUserNo);
                emitter.completeWithError(e);
            }
        }

    }

    private <T> void sendToClient(Long roomUserNo, T data, String comment, String type) {
        SseEmitter emitter = emitterRepository.get(roomUserNo);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(roomUserNo))
                        .name(type)
                        .reconnectTime(RECONNECTION_TIMEOUT)
                        .data(data)
                        .comment(comment));
            } catch (IOException e) {
                emitterRepository.deleteById(roomUserNo);
                emitter.completeWithError(e);
            }
        }
    }

    private SseEmitter createEmitter(Long roomUserNo) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(roomUserNo, emitter);

        emitter.onCompletion(() -> emitterRepository.deleteById(roomUserNo));
        emitter.onTimeout(() -> emitterRepository.deleteById(roomUserNo));

        return emitter;
    }

    private RoomUser validUser(Long roomUserNo) {

        return roomUserQueryRepository.findById(roomUserNo).orElseThrow(() -> new RoomException("해당 유저가 존재하지 않습니다."));
    }
}
