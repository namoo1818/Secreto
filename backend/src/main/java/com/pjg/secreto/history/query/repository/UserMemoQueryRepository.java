package com.pjg.secreto.history.query.repository;

import com.pjg.secreto.history.common.entity.UserMemo;
import com.pjg.secreto.mission.query.dto.SearchMemoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMemoQueryRepository extends JpaRepository<UserMemo, Long> {

    @Query("select um from UserMemo um where um.roomUser.id = :roomUserNo")
    UserMemo findByRoomUserNo(Long roomUserNo);
}
