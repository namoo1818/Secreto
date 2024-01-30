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
@Table(name = "tbl_matching")
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_no")
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_user_no")
    private RoomUser roomUser;

    private Long manitoNo;

    private Long manitiNo;

    private LocalDateTime matchingAt;

    private LocalDateTime deprecatedAt;

    @Builder
    public Matching(RoomUser roomUser, Long manitoNo, Long manitiNo, LocalDateTime matchingAt, LocalDateTime deprecatedAt) {

        this.roomUser = roomUser;
        this.manitoNo = manitoNo;
        this.manitiNo = manitiNo;
        this.matchingAt = matchingAt;
        this.deprecatedAt = deprecatedAt;
    }

}
