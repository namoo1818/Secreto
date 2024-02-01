package com.pjg.secreto.user.command.controller;

import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.user.command.dto.*;
import com.pjg.secreto.user.command.service.UserCommandService;
import com.pjg.secreto.user.common.dto.EmailValidationResponseDto;
import com.pjg.secreto.user.common.service.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Example", description = "Example API")
@RestController
@RequiredArgsConstructor
public class UserCommandController {

    private final UserCommandService userCommandService;
    private final JwtService jwtService;

    @PostMapping("/users/sign-up")
    public ResponseEntity<?> join(@RequestBody @Valid JoinRequestDto dto){
        userCommandService.register(dto);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "회원가입이 완료되었습니다.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/delete/")
    public ResponseEntity<?> withdraw(@RequestBody @Valid WithdrawRequestDto dto,
                                      Authentication authentication){

        userCommandService.withdraw(dto, authentication);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "지금까지 이용해주셔서 감사합니다.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/modify/")
    public ResponseEntity<?> modify(@RequestBody @Valid  ModifyRequestDto dto, Authentication authentication){
        userCommandService.modify(dto, authentication);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 수정되었습니다.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/password/request")
    public ResponseEntity<?> requestChangePassword(@RequestBody @Valid  ChangePasswordRequestDto dto){
        userCommandService.sendPasswordChangeEmail(dto);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "비밀번호 변경을 위한 페이지 링크를 이메일로 발송하였습니다.");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/password/reset/")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid PasswordResetRequestDto dto){
        userCommandService.resetPassword(dto);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "비밀번호 변경에 성공하였습니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cert/{userId}")
    public ResponseEntity<?> createCertCode(@PathVariable @NotBlank String userId){
        EmailValidationResponseDto result = userCommandService.sendEmailValidationMail(userId);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "이메일에 검증코드가 발송되었습니다. 확인 해주세요", result);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/password")
    public ResponseEntity<?> changePassword(@RequestBody @Valid ChangeLegacyPasswordRequestDto dto){
        userCommandService.changePassword(dto);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "비밀번호 변경에 성공하였습니다.");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/refreshAccess")
    public ResponseEntity<?> refreshAccessToken(@RequestHeader @NotBlank String RefreshToken){
        RefreshTokensResponseDto refreshToken = userCommandService.refreshToken(RefreshToken);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 토큰을 갱신 하였습니다.", refreshToken);
        return ResponseEntity.ok(response);
    }

}
