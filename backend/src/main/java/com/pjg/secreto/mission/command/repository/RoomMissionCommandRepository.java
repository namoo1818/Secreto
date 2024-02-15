package com.pjg.secreto.mission.command.repository;

import com.pjg.secreto.mission.common.entity.RoomMission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomMissionCommandRepository extends JpaRepository<RoomMission, Long> {
}
