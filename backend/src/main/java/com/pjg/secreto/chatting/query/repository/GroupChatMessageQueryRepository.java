package com.pjg.secreto.chatting.query.repository;

import com.pjg.secreto.chatting.common.entity.GroupChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupChatMessageQueryRepository extends JpaRepository<GroupChatMessage, Long> {
}
