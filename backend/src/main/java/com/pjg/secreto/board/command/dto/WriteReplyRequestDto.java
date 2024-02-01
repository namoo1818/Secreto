package com.pjg.secreto.board.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
@AllArgsConstructor
public class WriteReplyRequestDto {
    private Long roomUserNo;
    private Long boardNo;
    private String contnet;
    private Long parentReplyNo;
    private Long tagUserNo;
    private Boolean annonymityYn;
}
