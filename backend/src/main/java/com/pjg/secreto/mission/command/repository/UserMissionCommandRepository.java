package com.pjg.secreto.mission.command.repository;

import com.pjg.secreto.mission.common.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMissionCommandRepository extends JpaRepository<UserMission, Long> {
}
