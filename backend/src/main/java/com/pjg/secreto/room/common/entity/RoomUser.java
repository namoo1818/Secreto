package com.pjg.secreto.room.common.entity;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardEntryLog;
import com.pjg.secreto.board.common.entity.Liked;
import com.pjg.secreto.chatting.common.entity.ChatUser;
import com.pjg.secreto.chatting.common.entity.GroupChat;
import com.pjg.secreto.history.common.entity.ManitoExpectLog;
import com.pjg.secreto.history.common.entity.Matching;
import com.pjg.secreto.history.common.entity.UserMemo;
import com.pjg.secreto.mission.common.entity.UserMission;
import com.pjg.secreto.user.common.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<GroupChat> groupChats = new ArrayList<>();

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<Board> boards = new ArrayList<>();

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<Liked> likeds = new ArrayList<>();

    @OneToMany(mappedBy = "roomUser", fetch = FetchType.LAZY)
    private List<BoardEntryLog> boardEntryLogs = new ArrayList<>();

    private String userEntryAt;

    private String userLeaveAt;

    private boolean standbyYn;

    private String nickname;

    private boolean bookmarkYn;

}