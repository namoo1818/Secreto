package com.pjg.secreto.chatting.command.repository;

import com.pjg.secreto.chatting.common.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageCommandRepository extends JpaRepository<ChatMessage, Long>  {
}
