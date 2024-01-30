package com.pjg.secreto.chatting.query.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupChatMessageQueryDSLRepository {
    private JpaRepository query;
}
