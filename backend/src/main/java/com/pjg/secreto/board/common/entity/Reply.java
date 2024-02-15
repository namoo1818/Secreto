package com.pjg.secreto.board.common.entity;

import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_reply")
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reply_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_user_no")
    private RoomUser roomUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no")
    private Board board;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime registerAt;

    private Long parentReplyNo;
    private Long tagUserNo;
    private Boolean anonymityYn;
    private Boolean deleteYn;
    private Boolean updateYn;

    @Builder
    public Reply(RoomUser roomUser, Board board, String content, LocalDateTime registerAt,
                 Long parentReplyNo, Long tagUserNo, Boolean anonymityYn,
                 Boolean deleteYn, Boolean updateYn) {
        this.roomUser = roomUser;
        this.board = board;
        this.content = content;
        this.registerAt = registerAt;
        this.parentReplyNo = parentReplyNo;
        this.tagUserNo = tagUserNo;
        this.anonymityYn = anonymityYn;
        this.deleteYn = deleteYn;
        this.updateYn= updateYn;
    }

    public void updateReply(String content, Boolean anonymityYn) {
        this.content = content;
        this.anonymityYn = anonymityYn;
        this.updateYn = true;
    }

    public void deleteReply(){
        this.deleteYn = true;
    }
}
