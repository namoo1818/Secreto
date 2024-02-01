package com.pjg.secreto.mission.query.repository;

import com.pjg.secreto.mission.common.entity.SuddenMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SuddenMissionQueryRepository extends JpaRepository<SuddenMission, Long> {

    @Query("select sm from SuddenMission sm where sm.room.id = :roomNo and sm.missionSubmitAt >= current_timestamp")
    List<SuddenMission> findAllByRoomNoAfterNow(Long roomNo);

    @Query("select sm from SuddenMission sm where sm.id = :suddenMissionNo and sm.room.id = :roomNo")
    SuddenMission findByIdAndRoomNo(Long suddenMissionNo, Long roomNo);
}
