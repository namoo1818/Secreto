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
    private boolean predict_result;

    @QueryProjection
    public PredictBoardDto(PlayerDto manito, PlayerDto maniti, boolean predict_result) {
        this.manito = manito;
        this.maniti = maniti;
        this.predict_result = predict_result;
    }
}
