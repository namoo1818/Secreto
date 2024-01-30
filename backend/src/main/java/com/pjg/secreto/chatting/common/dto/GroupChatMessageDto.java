package com.pjg.secreto.chatting.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Getter
@Repository
@NoArgsConstructor
@AllArgsConstructor
public class GroupChatMessageDto {
    long groupChatMessageNo;
    long groupChatNo;
    long roomNo;
    String message;
    String sendAt;
    String sender;
}
