package com.pjg.secreto.board.command.dto;

import lombok.Data;

@Data
public class UpdateReplyRequestDto {
    private String content;
    private Long tagUserNo;
    private Boolean anonymityYn;
}
