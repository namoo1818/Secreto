package com.pjg.secreto.user.query.controller;

import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.user.common.dto.PrincipalUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.dto.UserInfo;
import com.pjg.secreto.user.query.dto.*;
import com.pjg.secreto.user.query.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserQueryController {

    private final UserQueryService queryService;

    @PostMapping("/users/log-in")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto){
        LoginResponseDto result = queryService.login(dto);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "로그인에 성공하였습니다.", result);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/log-out")
    public ResponseEntity<?> logout(@RequestBody LogOutRequestDto dto){
        queryService.logOut(dto);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "로그아웃 하셨습니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<?> detail(@PathVariable String userId, @AuthenticationPrincipal Authentication authentication){

        Map<String, Object> result = new HashMap<>();
        result.put("userInfo", new UserInfo(
                "",
                "hm_son7@naver.com",
                "손흥민",
                "https://i.namu.wiki/i/_BVQ0GmKg_SW5_wWhgZPO1v_A6w7kGGPBww_5HaSQJcxl-QMHqzgqd1143pU8jsvEvD-G03lBPf24ZekZ875NPFyLaeQx6RxPGb-S0GFwkhHS1psHxaK_BkThCl40V-MEY-g2dZp8rHaTCrzA_CD5w.webp"
        ));

        System.out.println(authentication);;

        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 반환하였습니다.", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/password/{certCode}")
    public ResponseEntity<?> allowChangePassword(@PathVariable String certCode){
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "비밀번호를 변경하는 페이지로 이동합니다.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cert")
    public ResponseEntity<?> validateCertCode(@RequestBody ValidateCertRequestDto dto){
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "이메일이 정상적으로 검증되었습니다.");
        return ResponseEntity.ok(response);
    }
}
