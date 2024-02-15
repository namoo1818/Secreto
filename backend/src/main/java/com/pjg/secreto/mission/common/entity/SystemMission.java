package com.pjg.secreto.mission.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_system_mission")
public class SystemMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_mission_no")
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;
}
