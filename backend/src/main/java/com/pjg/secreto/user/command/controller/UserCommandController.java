package com.pjg.secreto.user.command.controller;

import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.user.command.dto.*;
import com.pjg.secreto.user.command.service.UserCommandService;
import com.pjg.secreto.user.common.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userCommandService;
    private final JwtService jwtService;

    @PostMapping("/users/sign-up")
    public ResponseEntity<?> join(@RequestBody JoinRequestDto dto){
        userCommandService.register(dto);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "회원가입이 완료되었습니다.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/delete/")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawRequestDto dto){

        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "지금까지 이용해주셔서 감사합니다.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/modify/")
    public ResponseEntity<?> modify(@RequestBody ModifyRequestDto dto){

        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 수정되었습니다.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/password/request")
    public ResponseEntity<?> requestChangePassword(@RequestBody ChangePasswordRequestDto dto){

        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "비밀번호 변경을 위한 페이지 링크를 이메일로 발송하였습니다.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/password/change/{userId}")
    public ResponseEntity<?> resetPassword(@PathVariable Long userId){

        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "비밀번호 변경에 성공하였습니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cert/{userId}")
    public ResponseEntity<?> createCertCode(@PathVariable String userId){
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "이메일에 검증코드가 발송되었습니다. 확인 해주세요");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangeLegacyPasswordRequestDto dto){

        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "비밀번호 변경에 성공하였습니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/refreshAccess")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader String RefreshToken){
//        Map<String, Object> result =new HashMap<>();
//        result.put("AccessToken", "123w1231232afasf");
//        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "AcessToken이 재발급 되었습니다.", result);

        RefreshTokensResponseDto response = userCommandService.refreshToken(RefreshToken);

        return ResponseEntity.ok(response);
    }

}
