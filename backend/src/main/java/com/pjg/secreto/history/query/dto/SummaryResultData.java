package com.pjg.secreto.history.query.dto;


import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SummaryResultData {
    private String title;
    private String nickName;
    private Long amount;
    private String contents;
    private LocalDateTime dateTime;
    private String profile_url;
    private Long id;

    @QueryProjection
    public SummaryResultData(String title, String nickName, Long amount, String contents, LocalDateTime dateTime, String profile_url) {
        this.title = title;
        this.nickName = nickName;
        this.amount = amount;
        this.contents = contents;
        this.dateTime = dateTime;
        this.profile_url = profile_url;
    }

    @QueryProjection
    public SummaryResultData(String title, String nickName, Long amount, String contents, LocalDateTime dateTime, String profile_url, Long id) {
        this.title = title;
        this.nickName = nickName;
        this.amount = amount;
        this.contents = contents;
        this.dateTime = dateTime;
        this.profile_url = profile_url;
        this.id = id;
    }

    @QueryProjection
    public SummaryResultData(String nickName, Long amount, String profile_url) {
        this.nickName = nickName;
        this.amount = amount;
        this.profile_url = profile_url;
    }

    @QueryProjection
    public SummaryResultData(String nickName, LocalDateTime dateTime, String profile_url) {
        this.nickName = nickName;
        this.dateTime = dateTime;
        this.profile_url = profile_url;
    }

    @QueryProjection
    public SummaryResultData(String nickName, String contents, String profile_url) {
        this.nickName = nickName;
        this.contents = contents;
        this.profile_url = profile_url;
    }
}
