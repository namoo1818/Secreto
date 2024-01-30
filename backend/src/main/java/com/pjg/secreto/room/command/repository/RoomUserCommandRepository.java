package com.pjg.secreto.room.command.repository;

import com.pjg.secreto.room.common.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomUserCommandRepository extends JpaRepository<RoomUser, Long> {

    @Modifying
    @Query("delete from RoomUser ru where ru.id in :roomUserNos")
    void deleteAllByIds(@Param("roomUserNos") List<Long> roomUserNos);
}
