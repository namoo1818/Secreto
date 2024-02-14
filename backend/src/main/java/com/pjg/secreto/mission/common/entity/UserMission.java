package com.pjg.secreto.mission.common.entity;

import com.pjg.secreto.mission.common.exception.MissionException;
import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_user_mission")
public class UserMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_mission_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_user_no")
    private RoomUser roomUser;

    private LocalDateTime missionReceivedAt;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MissionType missionType;

    private int missionRerollCount;

    private Boolean missionCertifyYn;

    private Long roomMissionNo;

    @Builder
    public UserMission(RoomUser roomUser, LocalDateTime missionReceivedAt, String content,
                       MissionType missionType, int missionRerollCount,
                       Boolean missionCertifyYn, Long roomMissionNo) {

        this.roomUser = roomUser;
        this.missionReceivedAt = missionReceivedAt;
        this.content = content;
        this.missionType = missionType;
        this.missionRerollCount = missionRerollCount;
        this.missionCertifyYn = missionCertifyYn;
        this.roomMissionNo = roomMissionNo;
    }

    public void updateUserMission() {
        this.missionCertifyYn = !this.missionCertifyYn;
    }

    public void rerollUserMission(String content, int missionRerollCount) {

        if(missionRerollCount == 0) {
            throw new MissionException("해당 미션은 더 이상 리롤할 수 없습니다.");
        }

        this.content = content;
        this.missionRerollCount = missionRerollCount - 1;
    }

}
