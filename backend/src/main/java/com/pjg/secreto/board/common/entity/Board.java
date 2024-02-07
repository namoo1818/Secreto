package com.pjg.secreto.board.common.entity;

import com.pjg.secreto.mission.common.entity.UserMission;
import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Data
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

    private String imgUrl;

    private LocalDateTime registerAt;

    private Long hit;

    @Enumerated(value = EnumType.STRING)
    private BoardCategory boardCategory;

    private Boolean publicYn;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_mission_no")
    private UserMission userMission;

    private Long likedCount;

    private String writer;
    @Builder
    public Board(RoomUser roomUser, String title, String content, String imgUrl, LocalDateTime registerAt, Long hit, BoardCategory boardCategory, Boolean publicYn, UserMission userMission, Long likedCount, String writer) {
        this.roomUser = roomUser;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.registerAt = registerAt;
        this.hit = hit;
        this.boardCategory = boardCategory;
        this.publicYn = publicYn;
        this.userMission = userMission;
        this.likedCount = likedCount;
        this.writer = writer;
    }

    public void updateBoard(String writer, Long id, String title, String content, String imgUrl, BoardCategory boardCategory, Boolean publicYn, UserMission userMission){
        this.writer = writer;
        this.id = id;
        this.title = title;
        this.content = content;
        this.imgUrl = imgUrl;
        this.boardCategory = boardCategory;
        this.publicYn = publicYn;
        this.userMission = userMission;
    }
    public void updateLikedCount(Long likedCount){
        this.likedCount = likedCount;
    }

    public void updateHit(Long hit){
        this.hit = hit;
    }
}