package com.pjg.secreto.mission.command.controller;

import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.mission.command.dto.*;
import com.pjg.secreto.mission.command.service.MissionCommandService;
import com.pjg.secreto.room.query.dto.SearchRoomUserListResponseDto;
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
public class MissionCommandController {

    private final MissionCommandService missionCommandService;

    @PostMapping("/sudden")
    public ResponseEntity<?> addSuddenMission(@RequestBody AddSuddenMissionRequestDto addSuddenMissionRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        missionCommandService.addSuddenMission(addSuddenMissionRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "돌발 미션을 추가하였습니다.", null));
    }

    @DeleteMapping("/sudden")
    public ResponseEntity<?> deleteSuddenMission(@RequestBody DeleteSuddenMissionRequestDto deleteSuddenMissionRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        missionCommandService.deleteSuddenMission(deleteSuddenMissionRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "돌발 미션을 삭제하였습니다.", null));
    }

    @PostMapping("/memo_user")
    public ResponseEntity<?> memoUser(@RequestBody MemoUserRequestDto memoUserRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = AuthUtils.getAuthenticatedUserId();
        memoUserRequestDto.setUserNo(userNo);
        MemoUserResponseDto result = missionCommandService.memoUser(memoUserRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "메모가 작성되었습니다.", result));
    }

    @PutMapping("/reroll")
    public ResponseEntity<?> rerollMission(@RequestBody RerollMissionRequestDto rerollMissionRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = AuthUtils.getAuthenticatedUserId();
        rerollMissionRequestDto.setUserNo(userNo);
        missionCommandService.rerollMission(rerollMissionRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "미션 수정이 완료되었습니다.", null));
    }

}

