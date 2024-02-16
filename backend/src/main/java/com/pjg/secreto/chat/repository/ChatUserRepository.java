package com.pjg.secreto.chat.repository;

import com.pjg.secreto.chat.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatUserRepository extends JpaRepository<ChatUser, Long> {

    @Query("select cu from ChatUser cu where cu.roomUser.id = :roomUserNo")
    List<ChatUser> findAllByRoomUserNo(Long roomUserNo);

    @Query("select cu from ChatUser cu where cu.chat.id = :chatNo")
    List<ChatUser> findAllByChatNo(Long chatNo);
}
