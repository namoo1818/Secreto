package com.pjg.secreto.room.query.repository;

import com.pjg.secreto.room.common.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomQueryRepository extends JpaRepository<Room, Long>, RoomQueryRepositoryCustom{

    Room findByEntryCode(String entryCode);

}
