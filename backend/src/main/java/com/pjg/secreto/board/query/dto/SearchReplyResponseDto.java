package com.pjg.secreto.board.query.dto;

import com.pjg.secreto.board.common.entity.Reply;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SearchReplyResponseDto {
    Long replyNo;
    String content;
    LocalDateTime registerAt;
    Long parentReplyNo;
    Long tagUserNo;
    String writer;
    Boolean annonymityYn;

    public static SearchReplyResponseDto toDto(final Reply reply){
        return SearchReplyResponseDto.builder()
                .replyNo(reply.getId())
                .content(reply.getContent())
                .registerAt(reply.getRegisterAt())
                .parentReplyNo(reply.getParentReplyNo())
                .tagUserNo(reply.getTagUserNo())
                .writer(reply.getWriter())
                .annonymityYn(reply.getAnnonymityYn())
                .build();
    }
}
