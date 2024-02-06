package com.pjg.secreto.chatting.common.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GroupChatDto {
    private Long groupChatNo;
    private Long roomNo;
}
