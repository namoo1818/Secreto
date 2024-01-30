package com.pjg.secreto.history.query.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SummaryDto {
    private String header;

    @JsonProperty("summary")
    private SummaryResultData summaryResultData;


}
