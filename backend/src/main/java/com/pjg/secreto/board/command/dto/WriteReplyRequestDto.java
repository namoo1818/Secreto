package com.pjg.secreto.board.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Builder
@Data
@AllArgsConstructor
public class WriteReplyRequestDto {
    private String content;
    private Long parentReplyNo;
    private boolean anonymityYn;
}
