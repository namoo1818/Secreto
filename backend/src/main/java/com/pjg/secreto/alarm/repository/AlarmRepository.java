package com.pjg.secreto.alarm.repository;

import com.pjg.secreto.alarm.common.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {


    @Query("select a from Alarm a join fetch a.roomUser where a.roomUser.id = :roomUserNo")
    List<Alarm> findAllByRoomUserNo(Long roomUserNo);

    @Query("select a from Alarm a where a.id = :alarmNo and a.roomUser.id = :roomUserNo")
    Alarm findByAlarmNoAndRoomUserNo(Long alarmNo, Long roomUserNo);
}

