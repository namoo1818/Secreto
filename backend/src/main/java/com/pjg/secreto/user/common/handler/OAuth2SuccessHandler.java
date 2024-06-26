package com.pjg.secreto.user.common.handler;

import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.user.command.repository.UserCommandRepository;
import com.pjg.secreto.user.common.Repository.RefreshTokenRepository;
import com.pjg.secreto.user.common.config.SecurityUtilConfig;
import com.pjg.secreto.user.common.dto.PrincipalUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.entity.RefreshToken;
import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.common.service.JwtService;
import com.pjg.secreto.user.query.repository.UserQueryRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final String redirectUrl = "https://i10a805.p.ssafy.io/oauth/redirect";
    private final JwtService jwtService;
    private final UserQueryRepository userQueryRepository;
    private final UserCommandRepository userCommandRepository;
    private final RefreshTokenRepository refreshTokenRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        ProviderUser providerUser = principal.providerUser();

        Long id = AuthUtils.getAuthenticatedUserId(authentication);
        log.info("id = " + id);

        String userEmail = providerUser.getEmail();

        String message = "ok";
        // 원래 요청 주소 가져오기

        String accessToken = jwtService.generateAccessToken(providerUser);
        String refreshToken = jwtService.generateRefreshToken(providerUser);

        User user = userQueryRepository.findByEmail(userEmail).orElseThrow();
        Optional<RefreshToken> userRefreshToken = refreshTokenRepository.findById(user.getEmail());

        if (userRefreshToken.isPresent()){
            RefreshToken tokens = userRefreshToken.get();
            tokens.setRefreshToken(refreshToken);
            refreshTokenRepository.save(tokens);
        }

        else {
            RefreshToken tokens = RefreshToken.builder()
                    .refreshToken(refreshToken)
                    .email(user.getEmail())
                    .registeredAt(LocalDateTime.now())
                    .build();

            RefreshToken savedRefreshToken = refreshTokenRepository.save(tokens);
        }


        String targetUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .queryParam("message", message)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
        super.getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
