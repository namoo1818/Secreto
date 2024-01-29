package com.pjg.secreto.user.common.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refreshToken" )
public class RefreshToken {

    @TimeToLive
    @Value("${jwt.refresh-token.expiration}")
    private Long timeToLive;

    @Id
    private Long id;

    @Indexed
    private String email;


    private String refreshToken;

}
