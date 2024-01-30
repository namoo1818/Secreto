package com.pjg.secreto.user.common.handler;

import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.user.command.repository.RefreshTokenCommandRepository;
import com.pjg.secreto.user.common.config.SecurityUtilConfig;
import com.pjg.secreto.user.common.dto.PrincipalUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.entity.RefreshToken;
import com.pjg.secreto.user.common.service.JwtService;
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

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final String redirectUrl = SecurityUtilConfig.RESPONSE_REDIRECT_URL;
    private final JwtService jwtService;
    private final RefreshTokenCommandRepository refreshTokenCommandRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        PrincipalUser principal = (PrincipalUser) authentication.getPrincipal();
        ProviderUser providerUser = principal.providerUser();

        String id = AuthUtils.getAuthenticatedUserId(authentication);
        log.info(id);

        String userEmail = providerUser.getEmail();

        String message = "ok";
        // 원래 요청 주소 가져오기

        String accessToken = jwtService.generateAccessToken(providerUser);
        String refreshToken = jwtService.generateRefreshToken(providerUser);

        refreshTokenCommandRepository.save(new RefreshToken(userEmail, refreshToken));

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
