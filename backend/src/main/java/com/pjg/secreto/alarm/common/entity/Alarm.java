package com.pjg.secreto.alarm.common.entity;

import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@Table(name = "tbl_alarm")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="alarm_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_user_no")
    private RoomUser roomUser;

    private String author;

    private String content;

    private LocalDateTime generatedAt;

    private Boolean readYn;

    @Builder
    public Alarm(RoomUser roomUser, String author, String content, LocalDateTime generatedAt, Boolean readYn) {

        this.roomUser = roomUser;
        this.author = author;
        this.content = content;
        this.generatedAt = generatedAt;
        this.readYn = readYn;
    }

    public void readAlarm() {
        this.readYn = true;
    }

}
