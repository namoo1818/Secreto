package com.pjg.secreto.user.command.service;


import com.pjg.secreto.user.command.dto.JoinRequestDto;
import com.pjg.secreto.user.command.repository.RefreshTokenCommandRepository;
import com.pjg.secreto.user.command.repository.UserCommandRepository;
import com.pjg.secreto.user.common.dto.PrincipalUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.entity.RefreshToken;
import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.common.exception.UserException;
import com.pjg.secreto.user.common.service.JwtService;
import com.pjg.secreto.user.query.repository.RefreshTokenQueryRepository;
import com.pjg.secreto.user.query.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.http.SecurityHeaders;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCommandServiceImpl implements UserCommandService {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenCommandRepository refreshTokenCommandRepository;
    private final RefreshTokenQueryRepository refreshTokenQueryRepository;

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
    public String refreshToken(String refreshToken) {
        // 우선 입력 받은 토큰이 유효한지 검증
        boolean tokenValid = jwtService.isTokenValid(refreshToken);
        if(!tokenValid) throw new UserException("리프레시 토큰이 만료되었습니다.");

        // 리프레스 토큰에 담긴 이메일 정보 확인
        String email = jwtService.extractEmail(refreshToken);
        if(email == null) throw new UserException("리프레스 토큰이 만료되었거나 유효하지 않습니다.");


        // 서버에 저장된 리프레시 토큰과 사용자가 가져온 리프레시 토큰이 동일한지 체크
        RefreshToken savedRefreshToken = refreshTokenQueryRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("리프레스 토큰이 만료되었습니다"));

        boolean isEqual = savedRefreshToken.getRefreshToken().equals(refreshToken);

        if(!isEqual) throw new UserException("리프레스 토큰이 만료되었습니다");

        // 만약 정상적으로 검증되었으면 액세스토큰 갱신
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(principal.providerUser());
        return accessToken;
    }

    private void validateDuplicatedEmail(String email) {
        Optional<User> findUser = userQueryRepository.findByEmail(email);
        if(findUser.isPresent()) throw new UserException("이미 가입된 회원입니다.");
    }
}
