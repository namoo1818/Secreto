package com.pjg.secreto.mission.command.repository;

import com.pjg.secreto.mission.common.entity.SuddenMission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuddenMissionCommandRepository extends JpaRepository<SuddenMission, Long> {

}
