package com.pjg.secreto.room.query.repository;

import com.pjg.secreto.room.common.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomQueryRepository extends JpaRepository<Room, Long>, RoomQueryRepositoryCustom{

    Room findByEntryCode(String entryCode);


    @Query("select r from Room r join fetch MissionSchedule ms join fetch RoomMission rm")
    List<Room> findAllWithMissionScheduleAndRoomMission();
}
