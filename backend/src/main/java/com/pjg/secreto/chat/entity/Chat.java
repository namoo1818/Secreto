package com.pjg.secreto.chat.entity;

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
@Table(name = "tbl_chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_no")
    private Long id;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    private String name;

    private LocalDateTime firstTime;

    @Builder
    public Chat(String name, LocalDateTime firstTime) {
        this.name = name;
        this.firstTime = firstTime;
    }

    public void updateFirstTime(LocalDateTime firstTime){
        this.firstTime = firstTime;
    }

}