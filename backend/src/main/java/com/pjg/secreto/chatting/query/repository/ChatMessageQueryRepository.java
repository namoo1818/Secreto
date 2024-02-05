package com.pjg.secreto.chatting.query.repository;

import com.pjg.secreto.chatting.common.entity.Chat;
import com.pjg.secreto.chatting.common.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageQueryRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllByChat(Chat chat);
}
