package com.pjg.secreto.chatting.query.repository;

import com.pjg.secreto.chatting.common.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatUserQueryRepository extends JpaRepository<ChatUser, Long> {
}
