package com.pjg.secreto.user.common.filter;

import com.pjg.secreto.user.common.service.CustomUserDetailService;
import com.pjg.secreto.user.common.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

//        String authorization= request.getHeader("Authorization");
//
//        //Authorization 헤더 검증
//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//
//            System.out.println("token null");
//            filterChain.doFilter(request, response);
//
//            //조건이 해당되면 메소드 종료 (필수)
//            return;
//        }
//        String email;
//        String accessToken = authorization.split(" ")[1];


//        final String accessTokenType = request.getHeader("AccessToken").split(" ")[0];
//        final String accessToken = request.getHeader("AccessToken").split(" ")[1];
//
//        final String refreshTokenType = request.getHeader("RefreshToken").split(" ")[0];
//        final String refreshToken = request.getHeader("RefreshToken").split(" ")[1];

        final String email;

        // 만약 타입이 Baarer 토큰타입 체크
//        if (!(isBearerType(accessTokenType) && isBearerType(refreshTokenType))) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        // 액세스토큰이 만료되었는지 여부 체크
//        if (!(jwtService.validateToken(accessToken)) && jwtService.validateToken(refreshToken)){
//            filterChain.doFilter(request, response);
//            return;
//        }

//        email = jwtService.extractEmail(accessToken);

//        if (email != null && isNotAuthenticated()) {
//
//            UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
//
//            Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(authToken);
//
//            System.out.println(authToken);
//        }
        filterChain.doFilter(request, response);
    }

    private static boolean isBearerType(String accessTokenType) {
        return accessTokenType == null || !accessTokenType.equals("bearer");
    }

    private boolean isNotAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }
}