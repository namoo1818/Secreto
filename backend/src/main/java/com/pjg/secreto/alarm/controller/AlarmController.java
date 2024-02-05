package com.pjg.secreto.alarm.controller;

import com.pjg.secreto.alarm.service.AlarmService;
import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping(value = "/subscribe/{roomUserNo}", produces = "text/event-stream; charset=UTF-8")
    public SseEmitter subscribe(@PathVariable Long roomUserNo) {

//        Long userNo = AuthUtils.getAuthenticatedUserId();

        SseEmitter result = alarmService.subscribe(roomUserNo);

        return result;
//        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "알림이 연결되었습니다.", result));
    }

}
