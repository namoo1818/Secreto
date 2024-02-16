package com.pjg.secreto.user.common.entity;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED )
@RedisHash(value = "refreshToken")
public class RefreshToken {
    @Id
    private String email;

    private String refreshToken;
    private LocalDateTime registeredAt;

    @Builder
    public RefreshToken(String email, String refreshToken, LocalDateTime registeredAt) {
        this.email = email;
        this.refreshToken = refreshToken;
        this.registeredAt = registeredAt;
    }
}
