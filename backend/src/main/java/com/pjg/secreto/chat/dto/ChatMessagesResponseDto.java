package com.pjg.secreto.chat.dto;

import com.pjg.secreto.chat.entity.ChattingUserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ChatMessagesResponseDto {
    private String profileUrl;
    private String nickname;
    private Long writerId;
    private LocalDateTime registeredAt;
    private String content;
    private ChattingUserType type;

    @Builder
    public ChatMessagesResponseDto(String profileUrl, String nickname, LocalDateTime registeredAt, String content, ChattingUserType type, Long writerId) {
        this.profileUrl = profileUrl;
        this.nickname = nickname;
        this.registeredAt = registeredAt;
        this.content = content;
        this.writerId = writerId;
        this.type = type;
        setNickName(nickname);
    }

    public void setNickName(String nickName){
        if (this.type.equals(ChattingUserType.ALL))
            this.nickname = nickName;

        else if (this.type.equals(ChattingUserType.MANITO))
            this.nickname = nickName + "님의 마니또";

        else
            this.nickname = nickName + "님의 마니띠";
    }
}
