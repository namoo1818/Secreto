package com.pjg.secreto.history.command.controller;


import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.history.command.dto.WriteManitoWordCloudRequest;
import com.pjg.secreto.history.command.service.WordCloudCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HistoryCommandController {
    private final WordCloudCommandService wordCloudCommandService;

    @PostMapping("/history/{roomId}/wordCloud")
    public ResponseEntity<?> writeManitoWorldCloud(@PathVariable Long roomId,
                                                   @RequestBody WriteManitoWordCloudRequest dto){
        wordCloudCommandService.writeWordCloudCommand(roomId, dto);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 작성되었습니다.");
        return ResponseEntity.ok(response);
    }

}
