package com.pjg.secreto.user.query.service;

import com.pjg.secreto.user.common.dto.PrincipalUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.dto.UserInfo;
import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.common.exception.UserException;
import com.pjg.secreto.user.common.service.JwtService;
import com.pjg.secreto.user.query.dto.LoginRequestDto;
import com.pjg.secreto.user.query.dto.LoginResponseDto;
import com.pjg.secreto.user.query.repository.UserQueryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserQueryServiceImpl implements UserQueryService {
    private final UserQueryRepository userQueryRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


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
    public LoginResponseDto login(LoginRequestDto dto) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        log.info(authentication.toString());
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        ProviderUser providerUser = principal.providerUser();

        String accessToken = jwtService.generateAccessToken(providerUser);
        String refreshToken = jwtService.generateRefreshToken(providerUser);
        UserInfo info = new UserInfo(providerUser.getProvider(),
                                    providerUser.getEmail(),
                                    providerUser.getUsername(),
                                    providerUser.getProfileUrl());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        LoginResponseDto result = LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userInfo(info)
                .build();

        return result;
    }

}
