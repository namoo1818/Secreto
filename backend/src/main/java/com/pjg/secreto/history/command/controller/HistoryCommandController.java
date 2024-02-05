package com.pjg.secreto.history.command.controller;


import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.history.command.dto.WriteManitoWordCloudRequest;
import com.pjg.secreto.history.command.service.HistoryCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HistoryCommandController {
    private final HistoryCommandService historyCommandService;

    @PostMapping("/history/{roomId}/wordCloud")
    public ResponseEntity<?> writeManitoWorldCloud(@PathVariable Long roomId,
                                                   @RequestBody WriteManitoWordCloudRequest Dto){
        historyCommandService.writeWorldCloud(roomId, Dto);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 작성되었습니다.");
        return ResponseEntity.ok(response);
    }

}
