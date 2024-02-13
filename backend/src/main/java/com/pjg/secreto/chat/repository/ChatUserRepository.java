package com.pjg.secreto.chat.repository;

import com.pjg.secreto.chat.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {
}
