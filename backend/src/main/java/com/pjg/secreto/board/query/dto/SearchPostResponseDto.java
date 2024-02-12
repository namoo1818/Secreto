package com.pjg.secreto.board.query.dto;

import com.pjg.secreto.board.common.entity.BoardCategory;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SearchPostResponseDto {
    Long boardNo;
    Long roomUserNo;
    String title;
    String content;
    LocalDateTime registerAt;
    Long hit;
    BoardCategory boardCategory;
    Boolean publicYn;
    Long likedCount;
    String writer;
    String writerEmail;
    String writerProfileUrl;
    Boolean likedYn;
    Long userMissionNo;
    String userMission;

}
