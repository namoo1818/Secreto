package com.pjg.secreto.user.common.handler;

import com.pjg.secreto.user.common.config.SecurityUtilConfig;
import com.pjg.secreto.user.common.dto.PrincipalUser;
import com.pjg.secreto.user.common.dto.ProviderUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class OAuth2FailHandler extends SimpleUrlAuthenticationFailureHandler {
    private String redirectUrl = SecurityUtilConfig.RESPONSE_REDIRECT_URL;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String accessToken = "";
        String refreshToken = "";
        String message = "fail";

        log.info(exception.getMessage().toString());

        String targetUrl = UriComponentsBuilder.fromUriString(redirectUrl)
                .queryParam("email", accessToken)
                .queryParam("name", refreshToken)
                .queryParam("message", message)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
        super.getRedirectStrategy().sendRedirect(request, response, targetUrl);


    }
}
