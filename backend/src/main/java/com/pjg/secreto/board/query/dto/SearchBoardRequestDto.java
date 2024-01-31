package com.pjg.secreto.board.query.dto;

import lombok.Data;
@Data
public class SearchBoardRequestDto {
    Long roomUserNo;
    String boardCategory;
    String title;
    String content;
    String imgUrl;
    String writer;
}
