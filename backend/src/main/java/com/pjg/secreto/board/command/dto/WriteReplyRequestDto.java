package com.pjg.secreto.board.command.dto;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.Reply;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WriteReplyRequestDto {
    private Board board;
    private String contnet;
    private Long parentReplyNo;
    private Long tagUserNo;
    private String writer;
    private Boolean annonymityYn;

    public Reply toEntity(){
        return Reply.builder()
                .board(board)
                .content(contnet)
                .registerAt(LocalDateTime.now())
                .parentReplyNo(parentReplyNo)
                .tagUserNo(tagUserNo)
                .writer(writer)
                .annonymityYn(annonymityYn)
                .build();
    }
}
