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
    private String boardCategory;
    private String missionCategory;
    private Boolean publicYn;
    private String writer;

    public Board toEntity(){
        return Board.builder()
                .roomUser(roomUser)
                .title(title)
                .content(content)
                .registerAt(LocalDateTime.now())
                .hit(0L)
                .boardCategory(BoardCategory.valueOf(boardCategory))
                .publicYn(publicYn)
                .missionCategory(missionCategory)
                .likedCount(0L)
                .writer(writer)
                .build();
    }

}
