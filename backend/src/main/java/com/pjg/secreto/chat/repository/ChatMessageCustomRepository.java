package com.pjg.secreto.chat.repository;

import com.pjg.secreto.chat.dto.ChatMessagesResponseDto;
import com.pjg.secreto.chat.entity.*;
import com.pjg.secreto.room.common.entity.QRoomUser;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import com.pjg.secreto.user.common.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ChatMessageCustomRepository {
    private final JPAQueryFactory query;

    public List<ChatMessagesResponseDto> getChatHistory(ChattingUserType roomType, Long chatRoomNo) {
        QChatUser chatUser = QChatUser.chatUser;
        QChat chat = QChat.chat;
        QRoomUser roomUser = QRoomUser.roomUser;
        QUser user = QUser.user;
        QChatMessage message = QChatMessage.chatMessage;

        List<ChatUser> chatUserList = query.selectFrom(chatUser)
                .join(chatUser.chat, chat).fetchJoin()
                .join(chatUser.roomUser, roomUser).fetchJoin()
                .join(chatUser.roomUser.user, user).fetchJoin()
                .where(chatUser.chat.id.eq(chatRoomNo))
                .fetch();

        List<ChatMessage> messages = query.selectFrom(message)
                .join(message.chat, chat).fetchJoin()
                .where(message.chat.id.eq(chatRoomNo))
                .fetch();

        Map<String, ChatUser> mapChatUser = chatUserList
                .stream()
                .collect(Collectors.toUnmodifiableMap(cu -> cu.getRoomUser().getNickname(), Function.identity()));


        System.out.println(mapChatUser);
        List<ChatMessagesResponseDto> result = messages.stream()
                .map(chatMessage -> {
                    String chats = chatMessage.getMessage();
                    LocalDateTime sendAt = chatMessage.getSendAt();
                    Long id = chatMessage.getId();
                    String sender = chatMessage.getSender();
                    System.out.println(sender + " " + mapChatUser.get(sender));
                    RoomUser roomUsers = mapChatUser.get(sender).getRoomUser();

                    return ChatMessagesResponseDto.builder()
                            .sender(roomUsers.getNickname())
                            .profileUrl(roomUsers.getUser().getProfileUrl())
                            .sendAt(sendAt)
                            .message(chats)
                            .senderId(chatMessage.getSenderId())
                            .type(roomType)
                            .build();
                }).toList();

        return result;
    }
}
