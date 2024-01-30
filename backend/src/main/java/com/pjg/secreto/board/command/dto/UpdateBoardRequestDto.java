package com.pjg.secreto.board.command.dto;

import lombok.Data;

@Data
public class UpdateBoardRequestDto {
    private Long boardNo;
    private String title;
    private String content;
    private String boardCategory;
    private Boolean publicYn;
}
