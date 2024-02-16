package com.pjg.secreto.board.query.dto;

import com.pjg.secreto.board.common.entity.BoardCategory;
import lombok.Data;
@Data
public class SearchBoardRequestDto {
    BoardCategory boardCategory;
    String title;
    String content;
    String writer;
}
