package com.pjg.secreto.board.command.dto;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardCategory;
import com.pjg.secreto.room.common.entity.RoomUser;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class WriteBoardRequestDto {
    private RoomUser roomUser;
    private String title;
    private String content;
    private String imgUrl;
    private String boardCategory;
    private String missionCategory;
    private Boolean publicYn;

    public Board toEntity(){
        return Board.builder()
                .roomUser(roomUser)
                .title(title)
                .content(content)
                .imgUrl(imgUrl)
                .registerAt(LocalDateTime.now())
                .hit(0L)
                .boardCategory(BoardCategory.from(boardCategory))
                .publicYn(publicYn)
                .missionCategory(missionCategory)
                .likedCount(0L)
                //추후수정
                .writer("writer")
                .build();
    }

}
