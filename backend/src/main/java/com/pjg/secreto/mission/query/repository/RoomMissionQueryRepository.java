package com.pjg.secreto.mission.query.repository;

import com.pjg.secreto.mission.common.entity.RoomMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomMissionQueryRepository extends JpaRepository<RoomMission, Long> {

    @Query("select rm from RoomMission rm where rm.room.id = :roomNo")
    List<RoomMission> findAllByRoomNo(Long roomNo);
}
