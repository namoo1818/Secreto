package com.pjg.secreto.board.command.dto;

import lombok.Data;

@Data
public class UpdateBoardRequestDto {
    private String title;
    private String content;
    private String imgUrl;
    private Long userMissionNo;
    private Boolean publicYn;
}
