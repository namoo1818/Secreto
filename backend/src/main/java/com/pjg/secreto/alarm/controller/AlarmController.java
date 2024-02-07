package com.pjg.secreto.alarm.controller;

import com.pjg.secreto.alarm.dto.AlarmDataDto;
import com.pjg.secreto.alarm.dto.ShowAlarmDto;
import com.pjg.secreto.alarm.service.AlarmService;
import com.pjg.secreto.alarm.service.EmitterService;
import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AlarmController {

    private final EmitterService emitterService;
    private final AlarmService alarmService;

    @GetMapping(value = "/subscribe/{roomUserNo}", produces = "text/event-stream; charset=UTF-8")
    public SseEmitter subscribe(@PathVariable Long roomUserNo) {

        SseEmitter result = emitterService.subscribe(roomUserNo);

        return result;
    }

    @PostMapping("/send-data/{id}")
    public void sendDataTest(@PathVariable Long id, @RequestBody AlarmDataDto alarmDataDto) {

        alarmDataDto.setGeneratedAt(LocalDateTime.now());
        emitterService.alarm(id, alarmDataDto, "알림 갔니 인성아", "message");
    }

    @GetMapping("/alarm-list/{roomNo}")
    public ResponseEntity<?> showAlarmList(@PathVariable Long roomNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = AuthUtils.getAuthenticatedUserId();
        List<AlarmDataDto> result = alarmService.showAlarmList(userNo, roomNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "알림 리스트를 조회하였습니다.", result));
    }

    @GetMapping("/alarm/{alarmNo}/room/{roomNo}")
    public ResponseEntity<?> showAlarm(@PathVariable Long alarmNo, @PathVariable Long roomNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = AuthUtils.getAuthenticatedUserId();
        ShowAlarmDto showAlarmDto = ShowAlarmDto.builder()
                .alarmNo(alarmNo)
                .roomNo(roomNo)
                .userNo(userNo).build();

        AlarmDataDto result = alarmService.showAlarm(showAlarmDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "알림을 조회하였습니다.", result));
    }

}
