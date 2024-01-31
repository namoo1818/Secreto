package com.pjg.secreto.user.command.service;


import com.pjg.secreto.user.command.dto.JoinRequestDto;
import com.pjg.secreto.user.command.dto.RefreshTokensResponseDto;
import com.pjg.secreto.user.command.repository.UserCommandRepository;
import com.pjg.secreto.user.common.Repository.RefreshTokenRepository;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.entity.RefreshToken;
import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.common.exception.UserException;
import com.pjg.secreto.user.common.service.JwtService;
import com.pjg.secreto.user.query.repository.UserQueryRepository;
import io.netty.util.internal.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCommandServiceImpl implements UserCommandService {
    private final UserCommandRepository userCommandRepository;
    private final UserQueryRepository userQueryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

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

        if (StringUtil.isNullOrEmpty(refreshToken)) {
            throw new UserException("잘못된 형태의 리프레시 토큰이 전달되었습니다.");
        }

        try {
            type = refreshToken.split(" ")[0];
            tokens = refreshToken.split(" ")[1];

        } catch (Exception e) {
            throw new UserException("잘못된 형태의 액세스 토큰이 전달되었습니다.");
        }

        // 만약 타입이 Baarer 토큰타입 체크
        if (type == null || !type.equals("bearer")) {
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


        RefreshToken findRefreshToken = refreshTokenRepository.findByUser(user)
                .orElseThrow(() -> new UserException("리프레시 토큰이 존재하지 않거나 만료되었습니다."));

        findRefreshToken.setRefreshToken(newRefreshToken);
        RefreshToken savedRefreshToken = refreshTokenRepository.save(findRefreshToken);

        return new RefreshTokensResponseDto(user, newAccessToken, newRefreshToken);
    }

    private void validateDuplicatedEmail(String email) {
        Optional<User> findUser = userQueryRepository.findByEmail(email);
        if(findUser.isPresent()) throw new UserException("이미 가입된 회원입니다.");
    }
}
