package com.pjg.secreto.room.command.repository;

import com.pjg.secreto.room.common.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomCommandRepository extends JpaRepository<Room, Long> {
}
