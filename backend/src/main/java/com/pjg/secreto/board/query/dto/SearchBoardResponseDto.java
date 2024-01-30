package com.pjg.secreto.board.query.dto;

import com.pjg.secreto.board.common.entity.Board;
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


    public static SearchBoardResponseDto toDto(final Board board){
        return SearchBoardResponseDto.builder()
                .boardNo(board.getId())
                .title(board.getTitle())
                .registerAt(board.getRegisterAt())
                .hit(board.getHit())
                .boardCategory(board.getBoardCategory())
                .publicYn(board.getPublicYn())
                .missionCategory(board.getMissionCategory())
                .likedCount(board.getLikedCount())
                .writer(board.getWriter())
                //추후 구현
                .writerEmail("")
                .writerProfileUrl("")
                .replyCount(board.getReplies().size())
                .build();
    }

}
