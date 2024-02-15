package com.pjg.secreto.history.query.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Data
@NoArgsConstructor
@Builder
public class PredictBoardDto {
    @JsonProperty("manito")
    private PlayerDto manito;

    @JsonProperty("maniti")
    private PlayerDto maniti;
    private boolean predictResult;

    @QueryProjection
    public PredictBoardDto(PlayerDto manito, PlayerDto maniti, boolean predictResult) {
        this.manito = manito;
        this.maniti = maniti;
        this.predictResult = predictResult;
    }
}
