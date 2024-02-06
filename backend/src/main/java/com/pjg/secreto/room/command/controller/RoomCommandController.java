package com.pjg.secreto.room.command.controller;

import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.room.command.dto.*;
import com.pjg.secreto.room.command.service.RoomCommandService;
import com.pjg.secreto.user.common.dto.PrincipalUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomCommandController {

    private final RoomCommandService roomCommandService;

    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequestDto createRoomRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = AuthUtils.getAuthenticatedUserId();
        createRoomRequestDto.setUserNo(userNo);
        CreateRoomResponseDto result = roomCommandService.createRoom(createRoomRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "방이 생성되었습니다.", result));
    }

    @PutMapping
    public ResponseEntity<?> changeRoomName(@RequestBody ChangeRoomNameRequestDto changeRoomNameRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        roomCommandService.changeRoomName(changeRoomNameRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "방 이름이 변경되었습니다.", null));
    }

    @PutMapping("/{roomNo}")
    public ResponseEntity<?> setRoom(@RequestBody SetRoomRequestDto setRoomRequestDto, @PathVariable Long roomNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        setRoomRequestDto.setRoomNo(roomNo);
        SetRoomResponseDto result = roomCommandService.setRoom(setRoomRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "방을 시작하였습니다.", result));
    }

    @PostMapping("/enter")
    public ResponseEntity<?> enterRoom(@RequestBody EnterRoomRequestDto enterRoomRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = AuthUtils.getAuthenticatedUserId();
        enterRoomRequestDto.setUserNo(userNo);
        Long result = roomCommandService.enterRoom(enterRoomRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "방에 입장하였습니다.", result));
    }

    @PutMapping("/exit")
    public ResponseEntity<?> exitRoom(@RequestBody ExitRoomRequestDto exitRoomRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = AuthUtils.getAuthenticatedUserId();
        exitRoomRequestDto.setUserNo(userNo);
        roomCommandService.exitRoom(exitRoomRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "방에서 나갔습니다.", null));
    }

    @PutMapping("/accept")
    public ResponseEntity<?> acceptUser(@RequestBody AcceptUserRequestDto acceptUserRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        roomCommandService.acceptUser(acceptUserRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "유저를 수락하였습니다.", null));
    }

    @DeleteMapping("/deny")
    public ResponseEntity<?> denyUser(@RequestBody DenyUserRequestDto denyUserRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        roomCommandService.denyUser(denyUserRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "유저를 거절하였습니다.", null));
    }

    @PutMapping("/deligate")
    public ResponseEntity<?> deligateAdmin(@RequestBody DeligateAdminRequestDto deligateAdminRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        roomCommandService.deligateAdmin(deligateAdminRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "관리자 권한을 위임하였습니다.", null));
    }

    @PutMapping("/bookmark")
    public ResponseEntity<?> bookmarkRoom(@RequestBody BookmarkRoomRequestDto bookmarkRoomRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = AuthUtils.getAuthenticatedUserId();
        bookmarkRoomRequestDto.setUserNo(userNo);
        roomCommandService.bookmarkRoom(bookmarkRoomRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "방을 즐겨찾기 하였습니다.", null));
    }

    @PutMapping("/terminate")
    public ResponseEntity<?> terminateRoom(@RequestBody TerminateRoomRequestDto terminateRoomRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        roomCommandService.terminateRoom(terminateRoomRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "방을 종료하였습니다.", null));
    }

    @PostMapping("/matching")
    public ResponseEntity<?> initMatching(@RequestBody InitMatchingRequestDto initMatchingRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        roomCommandService.initMatching(initMatchingRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "매칭을 완료하였습니다.", null));

    }

    @PutMapping("/matching")
    public ResponseEntity<?> insertMatching(@RequestBody InsertMatchingRequestDto insertMatchingRequestDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        roomCommandService.insertMatching(insertMatchingRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "매칭을 완료하였습니다.", null));

    }

}

