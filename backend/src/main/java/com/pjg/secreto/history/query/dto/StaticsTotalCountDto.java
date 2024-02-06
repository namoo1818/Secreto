package com.pjg.secreto.history.query.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class StaticsTotalCountDto {
    private long boardCount;
    private long likeCount;
    private long hitCount;
    private long repliesCount;

    @QueryProjection
    public StaticsTotalCountDto(long boardCount, long likeCount, long hitCount, long repliesCount) {
        this.boardCount = boardCount;
        this.likeCount = likeCount;
        this.hitCount = hitCount;
        this.repliesCount = repliesCount;
    }

    public long getTotalScore() {
        return (long) ((this.boardCount + this.hitCount + this.likeCount + this.repliesCount) * 0.25);
    }
}
