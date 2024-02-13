package com.pjg.secreto.chat.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="chat_message_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_no")
    private Chat chat;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime sendAt;

    private Boolean readYn;

    private String sender;

    @Builder
    public ChatMessage(Chat chat, String message, LocalDateTime sendAt, Boolean readYn, String sender){
        this.chat = chat;
        this.message = message;
        this.sendAt = sendAt;
        this.readYn = readYn;
        this.sender = sender;
    }

    public void update(Boolean readYn){
        this.readYn = readYn;
    }

}