package com.pjg.secreto.mission.command.repository;

import com.pjg.secreto.mission.common.entity.MissionSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionScheduleCommandRepository extends JpaRepository<MissionSchedule, Long> {
}
