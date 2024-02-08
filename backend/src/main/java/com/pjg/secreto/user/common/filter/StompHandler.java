package com.pjg.secreto.user.common.filter;

import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.common.exception.UserException;
import com.pjg.secreto.user.common.service.CustomUserDetailService;
import com.pjg.secreto.user.common.service.JwtService;
import com.pjg.secreto.user.query.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {
    private final JwtService jwtService;
    private final UserQueryRepository userQueryRepository;
    private final CustomUserDetailService userDetailService;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(accessor.getCommand() == StompCommand.CONNECT) {
            String auth = accessor.getFirstNativeHeader("AccessToken");

            if (auth == null || !auth.startsWith("bearer ")) {
                throw new UserException("접근권한이 없습니다.");
            }

            String accessToken = auth.split(" ")[1];

            if(!jwtService.validateToken(accessToken))
                throw new UserException("접근권한이 없습니다.");

            String email = jwtService.extractEmail(accessToken);
            User user = userQueryRepository.findByEmail(email)
                    .orElseThrow(() ->  new UserException("해당 유저를 찾을 수 없습니다."));
            UserDetails userDetails = userDetailService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            accessor.setUser(authentication);
        }
        return message;
    }
}

