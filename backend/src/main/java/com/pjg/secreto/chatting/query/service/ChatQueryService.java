package com.pjg.secreto.chatting.query.service;

import com.pjg.secreto.chatting.common.entity.ChatMessage;
import com.pjg.secreto.chatting.common.entity.GroupChatMessage;
import org.apache.logging.log4j.message.Message;

import java.util.List;

public interface ChatQueryService {
    public List<ChatMessage> loadMessage(ChatMessage chatMessage) throws Exception;
    public List<GroupChatMessage> loadGroupMessage(GroupChatMessage groupChatMessage) throws Exception;
}
