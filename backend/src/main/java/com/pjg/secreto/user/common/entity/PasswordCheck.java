package com.pjg.secreto.user.common.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "passwordCheck" )
public class PasswordCheck {

    @Indexed
    private String email;

    @Id
    private String validationCode;

    @Value("${password.expiration}")
    @TimeToLive
    private Long timeToLive;

    @Builder
    public PasswordCheck(String email, String validationCode) {
        this.email = email;
        this.validationCode = validationCode;
    }
}
