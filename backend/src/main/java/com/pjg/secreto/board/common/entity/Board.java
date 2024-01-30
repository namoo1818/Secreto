package com.pjg.secreto.board.common.entity;

import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "tbl_board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_user_no")
    private RoomUser roomUser;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Liked> likeds = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<BoardEntryLog> boardEntryLogs = new ArrayList<>();

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime registerAt;

    private Long hit;

    @Convert(converter = BoardCategoryConverter.class)
    private BoardCategory boardCategory;

    private Boolean publicYn;

    private String missionCategory;

    private Long likedCount;

    private String writer;

    @Builder
    public Board(RoomUser roomUser, String title, String content, LocalDateTime registerAt, Long hit, BoardCategory boardCategory, Boolean publicYn, String missionCategory, Long likedCount, String writer) {
        this.roomUser = roomUser;
        this.title = title;
        this.content = content;
        this.registerAt = registerAt;
        this.hit = hit;
        this.boardCategory = boardCategory;
        this.publicYn = publicYn;
        this.missionCategory = missionCategory;
        this.likedCount = likedCount;
        this.writer = writer;
    }

    public void updateBoard(Long id, String title, String content, String boardCategory, Boolean publicYn){
        this.id = id;
        this.title = title;
        this.content = content;
        this.boardCategory = BoardCategory.valueOf(boardCategory);
        this.publicYn = publicYn;
    }
}