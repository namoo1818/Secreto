package com.pjg.secreto.history.query.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PredictBoardDto {
    @JsonProperty("manito")
    private PlayerDto manito;

    @JsonProperty("maniti")
    private PlayerDto maniti;
    private boolean predict_result;

}
