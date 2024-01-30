package com.pjg.secreto.mission.common.entity;

import com.pjg.secreto.room.common.entity.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_mission_schedule")
public class MissionSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_schedule_no")
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_no")
    private Room room;

    private LocalDate missionSubmitAt;

    @Builder
    public MissionSchedule (Room room, LocalDate missionSubmitAt) {

        this.room = room;
        this.missionSubmitAt = missionSubmitAt;
    }
}
