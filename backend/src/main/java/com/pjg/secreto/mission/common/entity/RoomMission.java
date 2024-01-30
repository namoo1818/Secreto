package com.pjg.secreto.mission.common.entity;

import com.pjg.secreto.room.common.entity.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_room_mission")
public class RoomMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_mission_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_no")
    private Room room;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    public RoomMission(Room room, String content) {
        this.room = room;
        this.content = content;
    }

}
