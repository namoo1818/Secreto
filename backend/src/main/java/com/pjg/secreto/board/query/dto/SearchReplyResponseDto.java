package com.pjg.secreto.board.query.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SearchReplyResponseDto {
    Long replyNo;
    Long roomUserNo;
    String content;
    LocalDateTime registerAt;
    Long parentReplyNo;
    String tagUserNickname;
    String writer;
    String writerEmail;
    String writerProfileUrl;
    Boolean anonymityYn;
    Boolean deleteYn;
}
