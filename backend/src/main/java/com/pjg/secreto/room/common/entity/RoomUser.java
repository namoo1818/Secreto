package com.pjg.secreto.room.common.entity;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardEntryLog;
import com.pjg.secreto.board.common.entity.Liked;
import com.pjg.secreto.chat.entity.ChatUser;
import com.pjg.secreto.history.common.entity.ManitoExpectLog;
import com.pjg.secreto.history.common.entity.Matching;
import com.pjg.secreto.history.common.entity.UserMemo;
import com.pjg.secreto.mission.common.entity.UserMission;
import com.pjg.secreto.user.common.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="tbl_room_user")
public class RoomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_user_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_no")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private User user;

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<UserMission> userMissions = new ArrayList<>();

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<UserMemo> userMemos = new ArrayList<>();

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<ManitoExpectLog> manitoExpectLogs = new ArrayList<>();

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<Matching> matchings = new ArrayList<>();

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<ChatUser> chatUsers = new ArrayList<>();

//    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
//    private List<GroupChat> groupChats = new ArrayList<>();

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<Liked> likeds = new ArrayList<>();

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<BoardEntryLog> boardEntryLogs = new ArrayList<>();

    private LocalDateTime userEntryAt;

    private LocalDateTime userLeaveAt;

    private Boolean standbyYn;

    private String nickname;

    private Boolean bookmarkYn;

    private Long usersManito;

    private Long usersManiti;


    @Builder
    public RoomUser(User user, Room room, LocalDateTime userEntryAt, LocalDateTime userLeaveAt,
                    Boolean standByYn, String nickname, Boolean bookmarkYn, Long usersManito, Long usersManiti) {
        this.user = user;
        this.room = room;
        this.userEntryAt = userEntryAt;
        this.userLeaveAt = userLeaveAt;
        this.standbyYn = standByYn;
        this.nickname = nickname;
        this.bookmarkYn = bookmarkYn;
        this.usersManito = usersManito;
        this.usersManiti = usersManiti;
    }

    // 유저가 방을 나갈 때
    public void leave() {
        this.userLeaveAt = LocalDateTime.now();
        this.bookmarkYn = false;
        this.usersManito = null;
        this.usersManiti = null;
    }

    // 유저가 방 참여를 수락받았을 때
    public void accepted() {
        this.userEntryAt = LocalDateTime.now();
        this.standbyYn = false;
    }

    public void standBy(){
        this.userEntryAt = null;
        this.standbyYn = true;
    }

    public Boolean bookmark() {
        this.bookmarkYn = !this.bookmarkYn;

        return this.bookmarkYn;
    }

    public void setMatchingInfo(Long usersManito, Long usersManiti) {
        this.usersManito = usersManito;
        this.usersManiti = usersManiti;
    }

    public void setManito(Long manitoNo) {
        this.usersManito = manitoNo;
    }

    public void setManiti(Long manitiNo) {
        this.usersManiti = manitiNo;
    }

}
