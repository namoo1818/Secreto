package com.pjg.secreto.chatting.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Getter
@Repository
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    long chatMessageNo;
    long chatNo;
    String content;
    String sendAt;
    boolean readYn;
    String sender;
}
