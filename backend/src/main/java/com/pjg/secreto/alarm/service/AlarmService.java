package com.pjg.secreto.alarm.service;

import com.pjg.secreto.alarm.common.entity.Alarm;
import com.pjg.secreto.alarm.common.exception.AlarmException;
import com.pjg.secreto.alarm.dto.AlarmDataDto;
import com.pjg.secreto.alarm.dto.ShowAlarmDto;
import com.pjg.secreto.alarm.repository.AlarmRepository;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final RoomUserQueryRepository roomUserQueryRepository;

    public List<AlarmDataDto> showAlarmList(Long userNo, Long roomNo) {

        try {

            RoomUser findRoomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, roomNo)
                    .orElseThrow(() -> new AlarmException("유저가 존재하지 않습니다."));

            List<Alarm> findAlarms = alarmRepository.findAllByRoomUserNo(findRoomUser.getId());

            List<AlarmDataDto> results = new ArrayList<>();
            for(Alarm a : findAlarms) {

                results.add(AlarmDataDto.builder()
                        .alarmNo(a.getId())
                        .author(a.getAuthor())
                        .content(a.getContent())
                        .generatedAt(a.getGeneratedAt())
                        .readYn(a.getReadYn())
                        .roomUserNo(a.getRoomUser().getId()).build());
            }

            return results;
        } catch (Exception e) {
            throw new AlarmException(e.getMessage());
        }
    }

    public AlarmDataDto showAlarm(ShowAlarmDto showAlarmDto) {

        try {

            RoomUser findRoomUser = roomUserQueryRepository
                    .findByUserNoAndRoomNo(showAlarmDto.getUserNo(), showAlarmDto.getRoomNo())
                    .orElseThrow(() -> new AlarmException("해당 유저가 존재하지 않습니다."));

            Alarm findAlarm = alarmRepository.findByAlarmNoAndRoomUserNo(showAlarmDto.getAlarmNo(), findRoomUser.getId());

            findAlarm.readAlarm();

            AlarmDataDto result = AlarmDataDto.builder()
                    .roomUserNo(findAlarm.getId())
                    .author(findAlarm.getAuthor())
                    .content(findAlarm.getContent())
                    .readYn(findAlarm.getReadYn())
                    .generatedAt(findAlarm.getGeneratedAt()).build();

            return result;

        } catch (Exception e) {
            throw new AlarmException(e.getMessage());
        }

    }
}
