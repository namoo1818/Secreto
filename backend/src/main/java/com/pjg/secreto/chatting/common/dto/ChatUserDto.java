package com.pjg.secreto.chatting.common.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatUserDto {
    private Long chatUsrNo;
    private Long chatNo;
    private Long userNo;
    private Long roomNo;
    private String userType;
}
