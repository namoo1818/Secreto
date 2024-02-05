package com.pjg.secreto.user.common.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjg.secreto.user.common.filter.JwtAuthenticationFilter;
import com.pjg.secreto.user.common.handler.Http401Handler;
import com.pjg.secreto.user.common.handler.Http403Handler;
import com.pjg.secreto.user.common.handler.OAuth2FailHandler;
import com.pjg.secreto.user.common.handler.OAuth2SuccessHandler;
import com.pjg.secreto.user.common.service.CustomOidcUserService;
import com.pjg.secreto.user.common.service.CustomUserDetailService;
import com.pjg.secreto.user.common.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.client.OAuth2LoginConfigurer;
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
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ObjectMapper objectMapper;
    private final OAuth2SuccessHandler successHandler;

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .requestMatchers("/static/js/**", "/static/images/**", "/static/css/**", "/static/scss/**")
//                .requestMatchers("/users/sign-up", "/users/log-in" ,"/users/refreshAccess",
//                        "/cert/**", "/users/password/**","/oauth2/**");
//    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/users/sign-up", "/users/log-in" ,"/users/refreshAccess",
                                "/cert/**", "/users/password/**","/oauth2/**",
                                "/chat/**").permitAll()
                        .anyRequest().permitAll());

        http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(customUserDetailService);

        http.exceptionHandling(e -> {
            e.accessDeniedHandler(new Http403Handler(objectMapper));
            e.authenticationEntryPoint(new Http401Handler(objectMapper));
        });

        http.oauth2Login(oauth2 -> {
            oauth2.userInfoEndpoint(
                    userInfoEndpointConfig -> {
                        userInfoEndpointConfig.oidcUserService(customOidcUserService);
                    });
            oauth2.successHandler(successHandler);
            oauth2.failureHandler(new OAuth2FailHandler());
        });

        return http.build();
    }
}

