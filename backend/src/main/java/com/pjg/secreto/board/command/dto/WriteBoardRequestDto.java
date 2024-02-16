package com.pjg.secreto.board.command.dto;

import com.pjg.secreto.board.common.entity.BoardCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class WriteBoardRequestDto {
    private String title;
    private String content;
    private String imgUrl;
    private BoardCategory boardCategory;
    private Long userMissionNo;
    private Boolean publicYn;

}
