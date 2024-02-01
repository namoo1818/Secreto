package com.pjg.secreto.user.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class EmailSendRequestDto {
    private String subject;
    private String to;
    private final String from = "pjg.secreto@gmail.com";
    private String contents;

    @Builder
    public EmailSendRequestDto(String subject, String to, String contents) {
        this.subject = subject;
        this.to = to;
        this.contents = contents;
    }
}
