package com.pjg.secreto.board.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "board_no")
    private Board board;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Convert(converter = BoardCategoryConverter.class)
    private LocalDateTime registerAt;

    private Long parentReplyNo;

    private Long tagUserNo;

    private String writer;

    private Boolean annonymityYn;

    @Builder
    public Reply(Board board, String content, LocalDateTime registerAt, Long parentReplyNo, Long tagUserNo, String writer, Boolean annonymityYn) {
        this.board = board;
        this.content = content;
        this.registerAt = registerAt;
        this.parentReplyNo = parentReplyNo;
        this.tagUserNo = tagUserNo;
        this.writer = writer;
        this.annonymityYn = annonymityYn;
    }
}
