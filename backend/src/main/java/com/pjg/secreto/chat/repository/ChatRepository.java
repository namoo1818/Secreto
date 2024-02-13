package com.pjg.secreto.chat.repository;

import com.pjg.secreto.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, Long> {
}

