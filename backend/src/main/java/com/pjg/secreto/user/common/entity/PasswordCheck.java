package com.pjg.secreto.user.common.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "passwordCheck" )
public class PasswordCheck {
    @Id
    private String email;

    private String validationCode;

    @Value("${password.expiration}")
    private Long timeToLive;

}
