package com.pjg.secreto.history.common.entity;

import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_manito_expect_log")
public class ManitoExpectLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manito_expect_log_no")
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_user_no")
    private RoomUser roomUser;

    private LocalDateTime expectedAt;

    private Long expectedUser;

    private String expectedReason;

    @Builder
    public ManitoExpectLog(RoomUser roomUser, LocalDateTime expectedAt, Long expectedUser, String expectedReason) {
        this.roomUser = roomUser;
        this.expectedAt = expectedAt;
        this.expectedUser = expectedUser;
        this.expectedReason = expectedReason;
    }

}
