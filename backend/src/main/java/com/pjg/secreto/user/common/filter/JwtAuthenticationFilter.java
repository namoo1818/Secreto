package com.pjg.secreto.user.common.filter;

import com.pjg.secreto.user.common.service.CustomUserDetailService;
import com.pjg.secreto.user.common.service.JwtService;
import com.pjg.secreto.user.query.service.UserQueryService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.beans.Transient;
import java.io.IOException;
import java.security.Principal;
import java.security.Security;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailService customUserDetailService) {
        this.jwtService = jwtService;
        this.customUserDetailService = customUserDetailService;
    }

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


        final String tokenType = request.getHeader("Type");
        final String accessToken = request.getHeader("AccessToken");
        final String email;

        // 만약 타입이 Baarer 토큰타입 체크
        if (tokenType == null || !tokenType.equals("bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 액세스토큰이 만료되었는지 여부 체크
        if (!jwtService.validateToken(accessToken)){
            filterChain.doFilter(request, response);
            return;
        }

        email = jwtService.extractEmail(accessToken);

        if (email != null && isNotAuthenticated()) {

            UserDetails userDetails = customUserDetailService.loadUserByUsername(email);

            Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

    private boolean isNotAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication() == null;
    }
}