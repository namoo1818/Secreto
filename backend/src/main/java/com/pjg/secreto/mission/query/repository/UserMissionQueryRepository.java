package com.pjg.secreto.mission.query.repository;

import com.pjg.secreto.mission.common.entity.UserMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserMissionQueryRepository extends JpaRepository<UserMission, Long> {

    @Query("select um from UserMission um where um.roomUser.id = :roomUserNo")
    List<UserMission> findByRoomUserNo(Long roomUserNo);

    Optional<UserMission> findById(Long userMissionNo);

    @Query("select um from UserMission um where um.missionReceivedAt = (select max(um.missionReceivedAt) from UserMission um)")
    UserMission findWhereLatestByRoomUserNo(Long roomUserNo);
}
