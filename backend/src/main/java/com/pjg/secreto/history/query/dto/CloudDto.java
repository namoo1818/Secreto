package com.pjg.secreto.history.query.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class CloudDto {
    private String text;
    private Long value;
}
