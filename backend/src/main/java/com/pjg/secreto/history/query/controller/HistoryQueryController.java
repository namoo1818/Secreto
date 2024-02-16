package com.pjg.secreto.history.query.controller;

import com.pjg.secreto.board.common.entity.BoardCategory;
import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.history.query.dto.*;
import com.pjg.secreto.history.query.service.HistoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("history")
@RequiredArgsConstructor
public class HistoryQueryController {
    private final HistoryQueryService historyQueryService;

    @GetMapping("/{roomId}/predict")
    public ResponseEntity<?> predictManittoResult(@PathVariable Long roomId) {
        Map<String, Object> result = new HashMap<>();
        List<PredictBoardDto> manitoResultList = historyQueryService.getManitoResultList(roomId);
        result.put("predict_history", manitoResultList);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 데이터를 로드하였습니다.", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}/summary")
    public ResponseEntity<?> summaryManitoResult(@PathVariable Long roomId) {
        Map<String, Object> result = new HashMap<>();
        List<SummaryDto> manitoStaticResult = historyQueryService.getManitoStaticResult(roomId);
        result.put("summaryResult", manitoStaticResult);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 데이터를 로드하였습니다.", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}/wordCloud")
    public ResponseEntity<?> searchManitoWorldCloud(@PathVariable Long roomId) {
        List<List<?>> result = historyQueryService.getWorldCloudContents(roomId);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 데이터를 로드하였습니다.", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}/manito")
    public ResponseEntity<?> collectManitoResult(@PathVariable Long roomId) {
        Map<String, Object> result = new HashMap<>();
        Long authenticatedUserId = AuthUtils.getAuthenticatedUserId();
        Map<String, Object> myManitoActivity = historyQueryService.getMyManitoActivity(roomId, authenticatedUserId);
        result.put("result", myManitoActivity);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 데이터를 조회했습니다.", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}/maniti")
    public ResponseEntity<?> collectManitiResult(@PathVariable Long roomId) {
        Map<String, Object> result = new HashMap<>();
        Long authenticatedUserId = AuthUtils.getAuthenticatedUserId();
        Map<String, Object> myManitiActivity = historyQueryService.getMyManitiActivity(roomId, authenticatedUserId);
        result.put("result", myManitiActivity);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 데이터를 조회했습니다.", result);
        return ResponseEntity.ok(response);
    }
}
