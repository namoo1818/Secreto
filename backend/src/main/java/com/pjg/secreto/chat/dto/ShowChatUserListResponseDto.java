package com.pjg.secreto.chat.dto;

import com.pjg.secreto.chat.entity.ChattingUserType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ShowChatUserListResponseDto {

    private Long chatUserNo;
    private Long chatNo;
    private Long roomUserNo;
    private ChattingUserType chattingUserType;
}
