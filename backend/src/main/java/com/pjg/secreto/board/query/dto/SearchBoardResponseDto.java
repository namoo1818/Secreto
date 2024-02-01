package com.pjg.secreto.board.query.dto;

import com.pjg.secreto.board.common.entity.BoardCategory;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SearchBoardResponseDto {
    Long boardNo;
    String title;
    LocalDateTime registerAt;
    Long hit;
    BoardCategory boardCategory;
    Boolean publicYn;
    String missionCategory;
    Long likedCount;
    String writer;
    String writerEmail;
    String writerProfileUrl;
    int replyCount;
    String imgUrl;
}
