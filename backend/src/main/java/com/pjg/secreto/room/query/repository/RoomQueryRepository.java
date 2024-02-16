package com.pjg.secreto.room.query.repository;

import com.pjg.secreto.room.common.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomQueryRepository extends JpaRepository<Room, Long>, RoomQueryRepositoryCustom{

    Room findByEntryCode(String entryCode);

//    @Query("SELECT DISTINCT r " +
//            "FROM Room r " +
//            "LEFT JOIN FETCH r.roomMissions rm " +
//            "LEFT JOIN FETCH r.missionSchedules ms ")
//    List<Room> findAllWithMissionScheduleAndRoomMission();

    @Query("select r from Room r join fetch r.missionSchedules ms")
    List<Room> findAllWithMissionSchedule();

    @Query("select r from Room r join fetch r.roomMissions rm where r in :hasMissionRooms")
    List<Room> findAllWithRoomMission(List<Room> hasMissionRooms);
}
