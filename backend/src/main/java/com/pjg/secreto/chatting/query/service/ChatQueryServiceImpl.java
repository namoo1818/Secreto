package com.pjg.secreto.chatting.query.service;

import com.pjg.secreto.chatting.command.repository.ChatCommandRepository;
import com.pjg.secreto.chatting.common.entity.ChatMessage;
import com.pjg.secreto.chatting.common.entity.GroupChatMessage;
import com.pjg.secreto.chatting.query.repository.ChatQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ChatQueryServiceImpl implements ChatQueryService{
    private ChatQueryRepository chatQueryRepository;
    private ChatCommandRepository chatCommandRepository;

    @Override
    public List<ChatMessage> loadMessage(ChatMessage chatMessage) throws Exception {
        return null;
    }

    @Override
    public List<GroupChatMessage> loadGroupMessage(GroupChatMessage groupChatMessage) throws Exception {
        return null;
    }
}
