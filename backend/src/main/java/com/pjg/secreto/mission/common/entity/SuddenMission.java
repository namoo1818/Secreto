package com.pjg.secreto.mission.common.entity;

import com.pjg.secreto.room.common.entity.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_sudden_mission")
public class SuddenMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sudden_mission_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_no")
    private Room room;

    private LocalDateTime missionSubmitAt;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    public SuddenMission(Room room, LocalDateTime missionSubmitAt, String content) {
        this.room = room;
        this.missionSubmitAt = missionSubmitAt;
        this.content = content;
    }

}
