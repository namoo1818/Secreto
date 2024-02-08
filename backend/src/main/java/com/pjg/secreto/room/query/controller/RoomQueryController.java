package com.pjg.secreto.room.query.controller;

import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.room.command.dto.AcceptUserRequestDto;
import com.pjg.secreto.room.query.dto.CheckCodeDto;
import com.pjg.secreto.room.query.dto.SearchRoomListResponseDto;
import com.pjg.secreto.room.query.dto.SearchRoomResponseDto;
import com.pjg.secreto.room.query.dto.SearchRoomUserListResponseDto;
import com.pjg.secreto.room.query.service.RoomQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.pjg.secreto.common.Util.AuthUtils.*;

@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
public class RoomQueryController {

    private final RoomQueryService roomQueryService;

    @PostMapping("/search")
    public ResponseEntity<?> enterRoom(@RequestBody CheckCodeDto checkCodeDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        boolean result = roomQueryService.enterRoom(checkCodeDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "방을 조회했습니다.", result));
    }

    // 방에 속한 모든 유저 조회
    @GetMapping("/user/{roomNo}")
    public ResponseEntity<?> searchRoomUserList(@PathVariable Long roomNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = getAuthenticatedUserId();
        List<SearchRoomUserListResponseDto> result = roomQueryService.searchRoomUserList(roomNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "유저 리스트를 조회하였습니다.", result));
    }

    // 유저가 속한 모든 방 조회 api
    @GetMapping("/user/user_room")
    public ResponseEntity<?> searchRoomList() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = getAuthenticatedUserId();

        List<SearchRoomListResponseDto> result = roomQueryService.searchRoomList(userNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "방 리스트를 조회하였습니다.", result));
    }

    // 룸 단건 조회 api
    @GetMapping("/{roomNo}")
    public ResponseEntity<?> searchRoom(@PathVariable Long roomNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long userNo = com.pjg.secreto.common.Util.AuthUtils.getAuthenticatedUserId();

        SearchRoomResponseDto result = roomQueryService.searchRoom(userNo, roomNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "방 정보를 조회하였습니다.", result));
    }

}
