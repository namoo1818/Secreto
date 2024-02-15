package com.pjg.secreto.chat.dto;

import com.pjg.secreto.chat.entity.ChattingUserType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatMessagesResponseDto {
    private String profileUrl;
    private String sender;
    private Long senderId;
    private LocalDateTime sendAt;
    private String message;
    private ChattingUserType type;

    @Builder
    public ChatMessagesResponseDto(String profileUrl, String sender, Long senderId, LocalDateTime sendAt, String message, ChattingUserType type) {
        this.profileUrl = profileUrl;
        this.sender = sender;
        this.senderId = senderId;
        this.sendAt = sendAt;
        this.message = message;
        this.type = type;
        setSender(sender);
    }



    public void setSender(String sender){
        if (this.type.equals(ChattingUserType.ALL))
            this.sender = sender;

        else if (this.type.equals(ChattingUserType.MANITO))
            this.sender = sender + "님의 마니또";

        else
            this.sender = sender + "님의 마니띠";
    }
}
