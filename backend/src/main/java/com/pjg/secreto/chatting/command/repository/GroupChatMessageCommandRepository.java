package com.pjg.secreto.chatting.command.repository;

import com.pjg.secreto.chatting.common.entity.GroupChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupChatMessageCommandRepository extends JpaRepository<GroupChatMessage, Long>  {
}
