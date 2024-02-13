package com.pjg.secreto.chat.service;

import com.pjg.secreto.chat.common.exception.ChatException;
import com.pjg.secreto.chat.dto.ChatMessageDto;
import com.pjg.secreto.chat.dto.CreateChatRoomRequestDto;
import com.pjg.secreto.chat.entity.Chat;
import com.pjg.secreto.chat.entity.ChatMessage;
import com.pjg.secreto.chat.repository.ChatMessageRepository;
import com.pjg.secreto.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;

    public void chatting(ChatMessageDto chatMessageDto) {

        try {

            Chat findChat = chatRepository.findById(chatMessageDto.getChatNo())
                    .orElseThrow(() -> new ChatException("ddd"));

            ChatMessage chatMessage = ChatMessage.builder()
                    .sender(chatMessageDto.getSender())
                    .message(chatMessageDto.getMessage())
                    .readYn(false)
                    .chat(findChat)
                    .sendAt(LocalDateTime.now()).build();

            chatMessageRepository.save(chatMessage);

        } catch (Exception e) {

            throw new ChatException(e.getMessage());
        }

    }

    public Long createChatRoom(CreateChatRoomRequestDto createChatRoomRequestDto) {

        try {

            Chat chat = Chat.builder()
                    .name(createChatRoomRequestDto.getName())
                    .firstTime(null).build();

            chatRepository.save(chat);

            return chat.getId();

        } catch(Exception e) {

            throw new ChatException(e.getMessage());
        }
    }
}
