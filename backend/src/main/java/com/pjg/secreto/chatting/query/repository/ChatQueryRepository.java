package com.pjg.secreto.chatting.query.repository;

import com.pjg.secreto.chatting.common.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatQueryRepository extends JpaRepository<Chat, Long> {
}
