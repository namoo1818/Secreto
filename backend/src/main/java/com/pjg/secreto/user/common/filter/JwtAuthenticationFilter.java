package com.pjg.secreto.user.common.filter;

import com.pjg.secreto.user.common.dto.PrincipalUser;
import com.pjg.secreto.user.common.exception.UserException;
import com.pjg.secreto.user.common.service.CustomUserDetailService;
import com.pjg.secreto.user.common.service.JwtService;
import com.pjg.secreto.user.query.service.UserQueryService;
import io.micrometer.common.util.StringUtils;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
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
    private final AccessDeniedHandler accessDeniedHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization= request.getHeader("AccessToken");
//        if(isWhiteList(request)){
//            filterChain.doFilter(request, response);
//        }

        log.info("1. requestURI={}",request.getRequestURI());

        //Authorization 헤더 검증
        if (StringUtils.isBlank(authorization) || !authorization.startsWith("bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        log.info("2. requestURI={}",request.getRequestURI());

        String token = authorization.split(" ")[1];
        log.info("3. token={}", token);


        //토큰 소멸 시간 검증
        if (!jwtService.validateToken(token)) {
//            filterChain.doFilter(request, response);
            accessDeniedHandler.handle(request, response, new AccessDeniedException(null));
            return;
        }


        String email = jwtService.extractEmail(token);
        log.info("4. extracted email={}", email);
        UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
        Authentication authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalUser p = (PrincipalUser) authentication.getPrincipal();
        log.info("5. principalUser={}", p);
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

}
