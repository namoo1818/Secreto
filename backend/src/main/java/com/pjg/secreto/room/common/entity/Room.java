package com.pjg.secreto.room.common.entity;

import com.pjg.secreto.history.common.entity.TotalResult;
import com.pjg.secreto.mission.common.entity.MissionSchedule;
import com.pjg.secreto.mission.common.entity.RoomMission;
import com.pjg.secreto.mission.common.entity.SuddenMission;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_no")
    private Long id;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<RoomUser> roomUsers = new ArrayList<>();

    @OneToOne(mappedBy = "room", fetch = FetchType.LAZY)
    private TotalResult totalResult;


    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<MissionSchedule> missionSchedules = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<RoomMission> roomMissions = new ArrayList<>();

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<SuddenMission> suddenMissions = new ArrayList<>();

    private String roomName;

    private String entryCode;

    private LocalDateTime roomStartAt;

    private LocalDateTime roomEndAt;

    private Boolean hostParticipantYn;

    private Boolean commonYn;

    private LocalTime missionSubmitTime;

    private LocalDate missionStartAt;

    private Boolean roomStartYn;

    private Long hostNo;

    @Builder
    public Room(String roomName, String entryCode, LocalDateTime roomStartAt, LocalDateTime roomEndAt,
                Boolean hostParticipantYn, Boolean commonYn, LocalTime missionSubmitTime,
                LocalDate missionStartAt, Boolean roomStartYn, Long hostNo) {

        this.roomName = roomName;
        this.entryCode = entryCode;
        this.roomStartAt = roomStartAt;
        this.roomEndAt = roomEndAt;
        this.hostParticipantYn = hostParticipantYn;
        this.commonYn = commonYn;
        this.missionSubmitTime = missionSubmitTime;
        this.missionStartAt = missionStartAt;
        this.roomStartYn = roomStartYn;
        this.hostNo = hostNo;
    }

    public void changeName(String roomName) {
        this.roomName = roomName;
    }

    public void changeHost(Long hostNo) {
        this.hostNo = hostNo;
    }

    public void startRoom(LocalDateTime roomStartAt, LocalDateTime roomEndAt, Boolean hostParticipantYn,
                          Boolean commonYn, LocalTime missionSubmitTime, LocalDate missionStartAt,
                          Boolean roomStartYn) {

        this.roomStartAt = roomStartAt;
        this.roomEndAt = roomEndAt;
        this.hostParticipantYn = hostParticipantYn;
        this.commonYn = commonYn;
        this.missionSubmitTime = missionSubmitTime;
        this.missionStartAt = missionStartAt;
        this.roomStartYn = roomStartYn;
    }

    public void terminateRoom() {
        this.roomEndAt = LocalDateTime.now();
    }

}
