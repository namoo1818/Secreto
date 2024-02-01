package com.pjg.secreto.user.common.filter;

import com.pjg.secreto.user.common.exception.UserException;
import com.pjg.secreto.user.common.service.CustomUserDetailService;
import com.pjg.secreto.user.common.service.JwtService;
import com.pjg.secreto.user.query.service.UserQueryService;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.beans.Transient;
import java.io.IOException;
import java.security.Principal;
import java.security.Security;
import java.util.List;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String authorization = request.getHeader("AccessToken");

        if (isWhiteList(request)) {
            filterChain.doFilter(request, response);
        }


        if (authorization == null || !authorization.startsWith("bearer ")) {

            log.info("token null");
            return;
        }

        String accessTokenType = null;
        String accessToken = null;

        accessTokenType = request.getHeader("AccessToken").split(" ")[0];
        accessToken = request.getHeader("AccessToken").split(" ")[1];

        if (!jwtService.isTokenValid(accessToken)){
            return;
        }

        String email = jwtService.extractEmail(accessToken);

        if (email != null) {
            UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
            Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info(authentication.toString());
        }
        filterChain.doFilter(request, response);
    }

    private static boolean isWhiteList(HttpServletRequest request) {
        List<AntPathRequestMatcher> whiteList = List.of(
                new AntPathRequestMatcher("/users/sign-up"),
                new AntPathRequestMatcher("/users/log-in"),
                new AntPathRequestMatcher("/users/refreshAccess"),
                new AntPathRequestMatcher("/cert/**"),
                new AntPathRequestMatcher("/users/password/**"),
                new AntPathRequestMatcher("/oauth2/**")
        );

        String servletPath = request.getServletPath();

        for (AntPathRequestMatcher matcher : whiteList) {
            if (matcher.matches(request)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isBearerType(String accessTokenType) {
        return accessTokenType != null || !accessTokenType.equals("bearer");
    }

    private boolean isNotAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }
}