package com.pjg.secreto.mission.query.controller;

import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.mission.command.dto.AddSuddenMissionRequestDto;
import com.pjg.secreto.mission.query.dto.*;
import com.pjg.secreto.mission.query.service.MissionQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RequestMapping("/mission")
@RequiredArgsConstructor
@RestController
public class MissionQueryController {

    private final MissionQueryService missionQueryService;

    @GetMapping
    public ResponseEntity<?> searchSystemMission() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        List<SearchSystemMissionResponseDto> result = missionQueryService.searchSystemMission();

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "시스템 미션을 조회하였습니다.", result));
    }

    @GetMapping("/sudden/{roomNo}")
    public ResponseEntity<?> searchSuddenMission(@PathVariable Long roomNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        List<SearchSuddenMissionResponseDto> result = missionQueryService.searchSuddenMission(roomNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "돌발 미션 리스트를 조회하였습니다.", result));
    }

    @GetMapping("/{roomNo}")
    public ResponseEntity<?> searchMissionList(@PathVariable Long roomNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        List<SearchMissionListResponseDto> result = missionQueryService.searchMissionList(roomNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "방의 미션 리스트를 조회하였습니다.", result));
    }

    @GetMapping("/user/{roomNo}")
    public ResponseEntity<?> searchUserMissionList(@PathVariable Long roomNo, SearchUserMissionListRequestDto searchUserMissionListRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = AuthUtils.getAuthenticatedUserId();
        searchUserMissionListRequestDto.setUserNo(userNo);
        searchUserMissionListRequestDto.setRoomNo(roomNo);
        List<SearchUserMissionListResponseDto> result = missionQueryService.searchUserMissionList(searchUserMissionListRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "유저 별 미션 리스트를 조회하였습니다.", result));
    }

    @GetMapping("/memo_user/{userMemoNo}")
    public ResponseEntity<?> searchMemo(@PathVariable Long userMemoNo, @RequestBody SearchMemoRequestDto searchMemoRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = AuthUtils.getAuthenticatedUserId();
        searchMemoRequestDto.setUserNo(userNo);
        searchMemoRequestDto.setUserMemoNo(userMemoNo);
        SearchMemoResponseDto result = missionQueryService.searchMemo(searchMemoRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "메모를 조회하였습니다.", result));
    }

}
