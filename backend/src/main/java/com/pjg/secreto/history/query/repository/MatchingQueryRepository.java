package com.pjg.secreto.history.query.repository;

import com.pjg.secreto.history.common.entity.Matching;
import com.pjg.secreto.room.common.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchingQueryRepository extends JpaRepository<Matching, Long> {
    Optional<Matching> findByRoomUser(RoomUser roomUser);

}
