package com.pjg.secreto.chatting.common.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_no")
    private Long id;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private List<ChatUser> chatUsers = new ArrayList<>();

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    private String firstTime;

}
