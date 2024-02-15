package com.pjg.secreto.user.common.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "emailCheck" )
public class EmailCheck {
    @Indexed
    private String email;

    @Id
    private String validationCode;


    @Value("${email.expiration}")
    @TimeToLive
    private Long timeToLive;

    public EmailCheck(String email, String validationCode) {
        this.email = email;
        this.validationCode = validationCode;
    }
}
