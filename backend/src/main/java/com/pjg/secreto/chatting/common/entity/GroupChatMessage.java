package com.pjg.secreto.chatting.common.entity;

import com.pjg.secreto.chatting.common.entity.GroupChat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tbl_group_chat_message")
public class GroupChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_chat_message_no")
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_chat_no")
    private GroupChat groupChat;

    @Column(columnDefinition = "TEXT")
    private String message;

    private String sendAt;

    private String sender;

}
