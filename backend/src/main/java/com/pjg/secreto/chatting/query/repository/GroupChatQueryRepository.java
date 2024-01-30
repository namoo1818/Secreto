package com.pjg.secreto.chatting.query.repository;

import com.pjg.secreto.chatting.common.entity.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupChatQueryRepository extends JpaRepository<GroupChat, Long> {
}
