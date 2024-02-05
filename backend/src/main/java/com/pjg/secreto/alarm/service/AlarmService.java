package com.pjg.secreto.alarm.service;

import com.pjg.secreto.alarm.repository.EmitterRepository;
import com.pjg.secreto.room.command.repository.RoomUserCommandRepository;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.common.exception.RoomException;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AlarmService {

    private final RoomUserQueryRepository roomUserQueryRepository;
    private final EmitterRepository emitterRepository;

    private static final Long DEFAULT_TIMEOUT = 600L * 1000 * 60;

    public SseEmitter subscribe(Long roomUserNo) {

        SseEmitter emitter = createEmitter(roomUserNo);

        sendToClient(roomUserNo, "roomUserNo : " + roomUserNo + "과의 알람 연결 성공", "sse 접속 성공");

        return emitter;

    }

    public <T> void alarm(Long userId, T data, String comment, String type) {
        sendToClient(userId, data, comment, type);
    }

    private void sendToClient(Long roomUserNo, Object data, String comment) {

        SseEmitter emitter = emitterRepository.get(roomUserNo);
        if(emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .id(String.valueOf(roomUserNo))
                        .name("sse")
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
