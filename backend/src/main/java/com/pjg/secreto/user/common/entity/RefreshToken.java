package com.pjg.secreto.user.common.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Entity
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
