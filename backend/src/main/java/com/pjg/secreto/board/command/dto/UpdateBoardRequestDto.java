package com.pjg.secreto.board.command.dto;

import com.pjg.secreto.board.common.entity.BoardCategory;
import lombok.Data;

@Data
public class UpdateBoardRequestDto {
    private String title;
    private String content;
    private String imgUrl;
    private BoardCategory boardCategory;
    private Long userMissionNo;
    private Boolean publicYn;
}
