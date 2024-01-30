package com.pjg.secreto.chatting.query.repository;

import com.pjg.secreto.chatting.common.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageQueryRepository extends JpaRepository<ChatMessage, Long> {
}
