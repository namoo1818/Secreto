package com.pjg.secreto.history.query.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SummaryResultData {
    private String title;
    private String nickname;
    private Long amount;
    private String contents;
    private LocalDateTime dateTime;
    private String imageUrl;
    private Long id;

    @QueryProjection
    public SummaryResultData(String title, String nickname, Long amount, String contents, LocalDateTime dateTime, String imageUrl) {
        this.title = title;
        this.nickname = nickname;
        this.amount = amount;
        this.contents = contents;
        this.dateTime = dateTime;
        this.imageUrl = imageUrl;
    }

    @QueryProjection
    public SummaryResultData(String title, String nickname, Long amount, String contents, LocalDateTime dateTime, String imageUrl, Long id) {
        this.title = title;
        this.nickname = nickname;
        this.amount = amount;
        this.contents = contents;
        this.dateTime = dateTime;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    @QueryProjection
    public SummaryResultData(String nickname, Long amount, String imageUrl) {
        this.nickname = nickname;
        this.amount = amount;
        this.imageUrl = imageUrl;
    }

    @QueryProjection
    public SummaryResultData(String nickname, LocalDateTime dateTime, String imageUrl) {
        this.nickname = nickname;
        this.dateTime = dateTime;
        this.imageUrl = imageUrl;
    }

    @QueryProjection
    public SummaryResultData(String nickname, String contents, String imageUrl) {
        this.nickname = nickname;
        this.contents = contents;
        this.imageUrl = imageUrl;
    }
}
