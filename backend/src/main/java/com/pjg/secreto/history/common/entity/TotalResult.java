package com.pjg.secreto.history.common.entity;

import com.pjg.secreto.room.common.entity.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_total_result")
public class TotalResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="total_result_no")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_no")
    private Room room;

    private int userCount;

    private int correctUserCount;

    private Long mostLikedBoardNo;

    private Long fastestCorrectUserNo;

    private Long mostWritedUser;

}
