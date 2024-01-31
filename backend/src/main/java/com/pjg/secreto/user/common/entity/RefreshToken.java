package com.pjg.secreto.user.common.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED )
@Table(name = "tbl_refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    private String refreshToken;

    private LocalDateTime registeredAt;

    @Builder
    public RefreshToken(User user, String refreshToken, LocalDateTime registeredAt) {
        this.user = user;
        this.refreshToken = refreshToken;
        this.registeredAt = registeredAt;
    }
}
