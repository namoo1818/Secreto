package com.pjg.secreto.user.query.service;

import com.pjg.secreto.user.command.repository.UserCommandRepository;
import com.pjg.secreto.user.common.Repository.EmailCheckRepository;
import com.pjg.secreto.user.common.Repository.EmailConfirmRepository;
import com.pjg.secreto.user.common.Repository.PasswordCheckRepository;
import com.pjg.secreto.user.common.Repository.RefreshTokenRepository;
import com.pjg.secreto.user.common.dto.PrincipalUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.dto.UserInfo;
import com.pjg.secreto.user.common.entity.*;
import com.pjg.secreto.user.common.exception.UserException;
import com.pjg.secreto.user.common.service.JwtService;
import com.pjg.secreto.user.query.dto.LogOutRequestDto;
import com.pjg.secreto.user.query.dto.LoginRequestDto;
import com.pjg.secreto.user.query.dto.LoginResponseDto;
import com.pjg.secreto.user.query.dto.ValidateCertRequestDto;
import com.pjg.secreto.user.query.repository.UserQueryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserQueryServiceImpl implements UserQueryService {
    private final UserQueryRepository userQueryRepository;
    private final UserCommandRepository userCommandRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordCheckRepository passwordCheckRepository;
    private final EmailConfirmRepository emailConfirmRepository;
    private final EmailCheckRepository emailCheckRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User findByEmail(String email) {
        return userQueryRepository.findByEmail(email)
                .orElseThrow( () -> new UserException("해당하는 회원을 조회할 수 없습니다."));
    }

    @Override
    public User findById(Long id) {
        return userQueryRepository.findById(id)
                .orElseThrow( () -> new UserException("해당하는 회원을 조회할 수 없습니다."));
    }

    @Override
    @Transactional
    public LoginResponseDto login(LoginRequestDto dto) {
        User user = userQueryRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserException("해당 유저를 찾을 수 없습니다."));

        if(user.isWithdrawalYn()) throw new UserException("해당 유저는 탈퇴된 유저입니다.");


        boolean isMatchPassword = passwordEncoder.matches(dto.getPassword(), user.getPassword());

        if(!isMatchPassword) throw new UserException("비밀번호가 일치하지 않습니다.");

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        ProviderUser providerUser = principal.providerUser();

        String accessToken = jwtService.generateAccessToken(providerUser);
        String refreshToken = jwtService.generateRefreshToken(providerUser);
        UserInfo info = new UserInfo(providerUser);

        Optional<RefreshToken> byUser = refreshTokenRepository.findById(user.getEmail());
        RefreshToken tokens = null;

        if(byUser.isPresent()){
            tokens = byUser.get();
            tokens.setRefreshToken(refreshToken);
        }

        else{
            tokens = RefreshToken.builder()
                    .email(user.getEmail())
                    .refreshToken(refreshToken)
                    .registeredAt(LocalDateTime.now())
                    .build();
        }

        assert tokens != null;
        refreshTokenRepository.save(tokens);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityContext context = SecurityContextHolder.getContext();
        log.info("로그인 {}",context.getAuthentication());
        PrincipalUser up = (PrincipalUser) context.getAuthentication().getPrincipal();
        log.info("로그인 등록 {}", up);

        LoginResponseDto result = LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userInfo(info)
                .build();

        return result;
    }

    @Override
    public void logOut(LogOutRequestDto dto) {
        User user = userQueryRepository.findByEmail(dto.getEmail()).orElseThrow();

        SecurityContext context = SecurityContextHolder.getContext();
        PrincipalUser up = (PrincipalUser) context.getAuthentication().getPrincipal();
        log.info("로그아웃 {}", up);

        log.info("로그아웃 서비스 {}", dto.getEmail());
        System.out.println(user.getId());
//        refreshTokenRepository.deleteById(dto.getEmail());
        SecurityContextHolder.clearContext();
        log.info("로그아웃 서비스 종료", dto.getEmail());
    }

    @Override
    public UserInfo detail(Authentication authentication) {
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        ProviderUser providerUser = principal.providerUser();
        UserInfo info = new UserInfo(providerUser);

        return info;
    }

    @Override
    public String allowChangePassword(String certCode) {
        PasswordCheck passwordCheck = passwordCheckRepository.findById(certCode)
                .orElseThrow(() -> new UserException("비밀번호 변경기간이 만료되었거나 비밀번호 변경 요청기록이 없습니다."));

        return passwordCheck.getEmail();
    }

    @Override
    public boolean validateDuplicatedEmail(ValidateCertRequestDto dto) {
        String validateCode = dto.getValidateCode();

        EmailCheck emailCheck = emailCheckRepository.findById(validateCode)
                .orElseThrow(() -> new UserException("해당 유저의 이메일 검증 요청을 확인할 수 없습니다."));
        EmailConfirm emailConfirm = emailConfirmRepository.findById(validateCode)
                .orElseThrow(() -> new UserException("검증코드가 올바르지 않습니다."));
        emailConfirm.setChecked(true);
        EmailConfirm save = emailConfirmRepository.save(emailConfirm);
        return true;
    }

}
