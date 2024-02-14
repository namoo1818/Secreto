package com.pjg.secreto.common.handler;

import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.common.exception.UserException;
import com.pjg.secreto.user.common.service.CustomUserDetailService;
import com.pjg.secreto.user.common.service.JwtService;
import com.pjg.secreto.user.query.repository.UserQueryRepository;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final UserQueryRepository userQueryRepository;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if(accessor.getCommand() == StompCommand.CONNECT) {
            final String auth = accessor.getFirstNativeHeader("AccessToken");

            System.out.println("##############");
            System.out.println(auth);

            if (auth == null || !auth.startsWith("bearer ")) {
                throw new UserException("접근권한이 없습니다.");
            }

            String accessToken = auth.split(" ")[1];

            System.out.println("##############");
            System.out.println(accessToken);

            if(!jwtService.validateToken(accessToken))
                throw new UserException("접근권한이 없습니다.");

            String email = jwtService.extractEmail(accessToken);
            User user = userQueryRepository.findByEmail(email)
                    .orElseThrow(() -> new UserException("해당 유저를 찾을 수 없습니다."));
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            accessor.setUser(authentication);
        }
        return message;
    }
}