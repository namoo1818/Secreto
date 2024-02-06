package com.pjg.secreto.history.query.dto;


import com.fasterxml.jackson.annotation.*;
import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardCategory;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
@JsonTypeName("post")
@EqualsAndHashCode
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class PostDto {
    private Long postId;
    private String title;
    private String content;
    private LocalDateTime entryAt;
    private String nickName;
    private long hit;
    private String thumbnail;
    private BoardCategory category;
    private String mission;

    @QueryProjection
    public PostDto(Board board) {
        this.postId = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.entryAt = board.getRegisterAt();
        this.nickName = board.getRoomUser().getNickname();
        this.hit = board.getHit();
        this.thumbnail = board.getImgUrl();
        this.category = board.getBoardCategory();
        this.mission = board.getMissionCategory();
    }
}
