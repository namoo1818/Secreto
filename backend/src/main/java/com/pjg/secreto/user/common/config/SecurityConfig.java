package com.pjg.secreto.user.common.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjg.secreto.user.common.filter.JwtAuthenticationFilter;
import com.pjg.secreto.user.common.handler.Http401Handler;
import com.pjg.secreto.user.common.handler.Http403Handler;
import com.pjg.secreto.user.common.service.CustomOidcUserService;
import com.pjg.secreto.user.common.service.CustomUserDetailService;
import com.pjg.secreto.user.common.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;
    private final CustomUserDetailService customUserDetailService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/users/sign-up", "/users/log-in" ,"/users/refreshAccess", "/cert/**", "/users/password/**").permitAll()
                        .anyRequest().authenticated());

        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        http.addFilterBefore(new JwtAuthenticationFilter(jwtService, customUserDetailService), UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(customUserDetailService);

        http.exceptionHandling(e -> {
            e.accessDeniedHandler(new Http403Handler(objectMapper));
            e.authenticationEntryPoint(new Http401Handler(objectMapper));
        });

        return http.build();
    }
}
