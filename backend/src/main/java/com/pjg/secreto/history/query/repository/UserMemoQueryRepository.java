package com.pjg.secreto.history.query.repository;

import com.pjg.secreto.history.common.entity.UserMemo;
import com.pjg.secreto.mission.query.dto.SearchMemoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMemoQueryRepository extends JpaRepository<UserMemo, Long> {

    Optional<UserMemo> findByMemoTo(Long memoTo);

    @Query("select um from UserMemo um where um.roomUser.id = :roomUserNo and um.memoTo = :memoTo")
    UserMemo findByRoomUserNoAndMemoTo(Long roomUserNo, Long memoTo);

    @Query("select um from UserMemo um where um.roomUser.id = :roomUserNo")
    List<UserMemo> findByRoomUserNo(Long roomUserNo);
}
