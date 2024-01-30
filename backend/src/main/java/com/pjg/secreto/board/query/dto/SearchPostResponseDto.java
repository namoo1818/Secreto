package com.pjg.secreto.board.query.dto;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardCategory;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SearchPostResponseDto {
    Long boardNo;
    String title;
    String content;
    LocalDateTime registerAt;
    Long hit;
    BoardCategory boardCategory;
    Boolean publicYn;
    String missionCategory;
    Long likedCount;
    String writer;

    public static SearchPostResponseDto toDto(final Board board){
        return SearchPostResponseDto.builder()
                .boardNo(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .registerAt(board.getRegisterAt())
                .hit(board.getHit())
                .boardCategory(board.getBoardCategory())
                .publicYn(board.getPublicYn())
                .missionCategory(board.getMissionCategory())
                .likedCount(board.getLikedCount())
                .writer(board.getWriter())
                .build();
    }

}
