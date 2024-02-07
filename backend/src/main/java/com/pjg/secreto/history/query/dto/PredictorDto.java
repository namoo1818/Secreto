package com.pjg.secreto.history.query.dto;


import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@JsonTypeName("predict")
@EqualsAndHashCode
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class PredictorDto {
    private Long id;
    private LocalDateTime entryAt;
    private String predictorNickname;
    private String targetNickName;
    private String predictReason;
    private boolean isCorrect;

    @QueryProjection
    public PredictorDto(Long id, LocalDateTime entryAt,  String predictorNickname, String targetNickName, String predictReason) {
        this.id = id;
        this.entryAt = entryAt;
        this.predictorNickname = predictorNickname;
        this.targetNickName = targetNickName;
        this.predictReason = predictReason;
    }
}
