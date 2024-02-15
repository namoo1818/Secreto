package com.pjg.secreto.board.common.entity;

import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_board_entry_log")
public class BoardEntryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_entry_log_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_user_no")
    private RoomUser roomUser;

    @CreatedDate
    private LocalDate entryAt;

    @Builder
    public BoardEntryLog(Board board, RoomUser roomUser, LocalDate entryAt) {
        this.board = board;
        this.roomUser = roomUser;
        this.entryAt = entryAt;
    }
}


