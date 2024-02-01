package com.pjg.secreto.user.command.service;


import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.common.Util.RandomUtils;
import com.pjg.secreto.user.common.dto.EmailSendRequestDto;
import com.pjg.secreto.user.command.dto.*;
import com.pjg.secreto.user.command.repository.EmailCheckCommandRepository;
import com.pjg.secreto.user.command.repository.UserCommandRepository;
import com.pjg.secreto.user.common.Repository.PasswordCheckRepository;
import com.pjg.secreto.user.common.Repository.RefreshTokenRepository;
import com.pjg.secreto.user.common.dto.EmailValidationResponseDto;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.entity.EmailCheck;
import com.pjg.secreto.user.common.entity.PasswordCheck;
import com.pjg.secreto.user.common.entity.RefreshToken;
import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.common.exception.UserException;
import com.pjg.secreto.user.common.service.JwtService;
import com.pjg.secreto.user.query.repository.UserQueryRepository;
import io.netty.util.internal.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserCommandServiceImpl implements UserCommandService {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmailSenderService emailSenderService;
    private final PasswordCheckRepository passwordCheckRepository;
    private final EmailCheckCommandRepository emailCheckCommandRepository;

    @Override
    public User register(ProviderUser target) {
        validateDuplicatedEmail(target.getEmail());
        User user = new User(target);
        return userCommandRepository.save(user);
    }

    @Override
    public User register(JoinRequestDto dto) {
        validateDuplicatedEmail(dto.getEmail());
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = dto.convertUser();
        return userCommandRepository.save(user);
    }

    @Override
    public RefreshTokensResponseDto refreshToken(String refreshToken) {
        String tokens;
        String type;

        if (StringUtil.isNullOrEmpty(refreshToken) || refreshToken.startsWith("bearer ")) {
            throw new UserException("잘못된 형태의 리프레시 토큰이 전달되었습니다.");
        }

        try {
            type = refreshToken.split(" ")[0];
            tokens = refreshToken.split(" ")[1];

        } catch (Exception e) {
            throw new UserException("잘못된 형태의 액세스 토큰이 전달되었습니다.");
        }

        // 우선 입력 받은 토큰이 유효한지 검증
        boolean tokenValid = jwtService.isTokenValid(tokens);
        if(!tokenValid) throw new UserException("리프레시 토큰이 만료되었습니다.");

        // 리프레스 토큰에 담긴 이메일 정보 확인
        String email = jwtService.extractEmail(tokens);

        if(email == null) throw new UserException("리프레스 토큰이 만료되었거나 유효하지 않습니다.");

        User user = userQueryRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("해당 유저를 찾을 수 없습니다."));

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);


        RefreshToken findRefreshToken = refreshTokenRepository.findById(user.getEmail())
                .orElseThrow(() -> new UserException("리프레시 토큰이 존재하지 않거나 만료되었습니다."));

        findRefreshToken.setRefreshToken(newRefreshToken);
        refreshTokenRepository.save(findRefreshToken);

        return new RefreshTokensResponseDto(user, newAccessToken, newRefreshToken);
    }

    @Override
    public void withdraw(WithdrawRequestDto dto, Authentication authentication) {
        String userEmail = AuthUtils.getAuthenticatedUserEmail(authentication);

        User user = userQueryRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserException("해당 유저를 조회할 수 없습니다."));

        user.setWithdrawalAt(LocalDateTime.now().toString());
        user.setWithdrawalYn(true);


        userCommandRepository.save(user);

        log.info(userEmail +"님의 탈퇴가 정상적으로 진행되었습니다.");
    }



    @Override
    public User modify(ModifyRequestDto dto, Authentication authentication) {
        String userEmail = AuthUtils.getAuthenticatedUserEmail(authentication);

        User user = userQueryRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserException("해당 유저를 조회할 수 없습니다."));

        user.setEmail(dto.getEmail());
        user.setProfileUrl(dto.getProfileUrl());

        User modifedUser = userCommandRepository.save(user);

        return modifedUser;
    }

    @Override
    public void sendPasswordChangeEmail(ChangePasswordRequestDto dto) {
        User user = userQueryRepository.findByEmailAndAndNickname(dto.getEmail(), dto.getName())
                .orElseThrow(() -> new UserException("해당 유저를 조회할 수 없습니다."));


        Optional<PasswordCheck> byId = passwordCheckRepository.findByEmail(dto.getEmail());
        if(byId.isPresent()) throw new UserException("비밀번호 변경 페이지가 이메일로 발송되었습니다. 발송되지 않았을 경우, 스팸 메일을 체크해주세요");

        String randomCode = RandomUtils.generateRandomCode(8);
        passwordCheckRepository.save(new PasswordCheck(dto.getEmail(), randomCode));

        String url = randomCode;

        EmailSendRequestDto emailSendDto = EmailSendRequestDto.builder()
                .to(dto.getEmail())
                .subject("비밀번호 변경 페이지로 안내합니다.")
                .contents("<a href=http://localhost/ " + url + ">").build();

        emailSenderService.sendMail(emailSendDto);
    }

    @Override
    public void resetPassword(PasswordResetRequestDto dto) {
        User user = userQueryRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserException("해당 유저를 찾을 수 없습니다."));

        user.setPassword(dto.getPassword());
        userCommandRepository.save(user);
    }

    @Override
    public void changePassword(ChangeLegacyPasswordRequestDto dto) {
        User user = userQueryRepository.findByEmailAndPassword(dto.getEmail(), dto.getOld_password())
                .orElseThrow(() -> new UserException("해당 유저를 찾을 수 없습니다."));

        user.setPassword(dto.getNew_password());
        userCommandRepository.save(user);
    }

    @Override
    public EmailValidationResponseDto sendEmailValidationMail(String userId) {
        Optional<EmailCheck> byEmail = emailCheckCommandRepository.findByEmail(userId);

        if(byEmail.isPresent()) throw new UserException("이미 이메일이 발송되었습니다. 메일을 확인해주세요!");

        String randomCode = RandomUtils.generateRandomCode(8);
        passwordCheckRepository.save(new PasswordCheck(userId, randomCode));

        String url = randomCode;

        EmailSendRequestDto emailSendDto = EmailSendRequestDto.builder()
                .to(userId)
                .subject("비밀번호 변경 페이지로 안내합니다.")
                .contents("<a href=http://localhost/ " + url + ">").build();

        emailSenderService.sendMail(emailSendDto);

        return new EmailValidationResponseDto();

    }

    private void validateDuplicatedEmail(String email) {
        Optional<User> findUser = userQueryRepository.findByEmail(email);
        if(findUser.isPresent()) throw new UserException("이미 가입된 회원입니다.");
    }
}
