package com.pjg.secreto.history.query.repository;

import com.pjg.secreto.history.common.entity.Matching;
import com.pjg.secreto.room.common.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MatchingQueryRepository extends JpaRepository<Matching, Long> {
    Optional<Matching> findByRoomUser(RoomUser roomUser);

    @Query("select m from Matching m where m.roomUser.id = :roomUserNo")
    List<Matching> findAllByRoomUserNo(Long roomUserNo);
}
