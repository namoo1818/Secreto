package com.pjg.secreto.chat.entity;

import com.pjg.secreto.room.common.entity.RoomUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_chat_user")
public class ChatUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_user_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_user_no")
    private RoomUser roomUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_no")
    private Chat chat;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ChattingUserType chattingUserType;


}
