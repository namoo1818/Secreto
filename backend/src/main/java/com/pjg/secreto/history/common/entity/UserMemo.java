package com.pjg.secreto.history.common.entity;

import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_user_memo")
public class UserMemo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_memo_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_user_no")
    private RoomUser roomUser;

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ManitoPredictType manitoPredictType;

    private Long memoTo;

    @Builder
    public UserMemo(RoomUser roomUser, String memo, ManitoPredictType manitoPredictType, Long memoTo) {
        this.roomUser = roomUser;
        this.memo = memo;
        this.manitoPredictType = manitoPredictType;
        this.memoTo = memoTo;
    }

    public void updateMemo(String memo, ManitoPredictType manitoPredictType) {
        this.memo = memo;
        this.manitoPredictType = manitoPredictType;
    }

    public void updateToUnknown() {
        this.manitoPredictType = ManitoPredictType.UNKNOWN;
    }

}
