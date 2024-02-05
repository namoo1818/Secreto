package com.pjg.secreto.history.command.controller;


import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.history.command.dto.WriteManitoWordCloudRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class HistoryCommandController {

    @PostMapping("/history/{roomId}/wordCloud")
    public ResponseEntity<?> writeManitoWorldCloud(@PathVariable Long roomId,
                                                   @RequestBody WriteManitoWordCloudRequest Dto){

        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 작성되었습니다.");
        return ResponseEntity.ok(response);
    }

}
