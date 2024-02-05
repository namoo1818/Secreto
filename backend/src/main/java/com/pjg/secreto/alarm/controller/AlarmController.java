package com.pjg.secreto.alarm.controller;

import com.pjg.secreto.alarm.dto.DataDto;
import com.pjg.secreto.alarm.service.AlarmService;
import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping(value = "/subscribe/{roomUserNo}", produces = "text/event-stream; charset=UTF-8")
    public SseEmitter subscribe(@PathVariable Long roomUserNo) {

        SseEmitter result = alarmService.subscribe(roomUserNo);

        return result;
//        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "알림이 연결되었습니다.", result));
    }

    @PostMapping("/send-data/{id}")
    public void sendDataTest(@PathVariable Long id, @RequestBody DataDto dataDto) {

        alarmService.alarm(id, dataDto, "알림 갔니 인성아", "test");
    }

}
