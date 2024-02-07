package com.pjg.secreto.chatting.command.service;

import com.pjg.secreto.chatting.common.entity.ChatMessage;
import com.pjg.secreto.chatting.common.entity.GroupChatMessage;

public interface ChatCommandService {
    public Long createChat(Long manitoNo, Long manitiNo);
    public Long createGroupChat(Long roomNo);
    public void saveMessage(ChatMessage chatMessage) throws Exception;
    public void saveGroupMessage(GroupChatMessage groupChatMessage) throws Exception;
}
