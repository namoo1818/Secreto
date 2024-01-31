package com.pjg.secreto.history.command.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EMailRequestDto {
    private String subject;
    private String to;
    private String from = "pjg.secreto@gmail.com";
    private String contents;
}
