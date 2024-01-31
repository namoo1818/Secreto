package com.pjg.secreto.board.command.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class WriteReplyRequestDto {
    private Long roomUserNo;
    private Long boardNo;
    private String contnet;
    private Long parentReplyNo;
    private Long tagUserNo;
    private Boolean annonymityYn;
}
