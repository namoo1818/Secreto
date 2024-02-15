package com.pjg.secreto.board.common.entity;

import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_liked")
public class Liked {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="liked_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_user_no")
    private RoomUser roomUser;

    @Builder
    public Liked(Board board, RoomUser roomUser){
        this.board = board;
        this.roomUser = roomUser;
    }
}
