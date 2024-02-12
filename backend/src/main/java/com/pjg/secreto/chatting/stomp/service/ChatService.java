//package com.pjg.secreto.chatting.stomp.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class ChatService {
//
////    private final ChatRespitory chatRespitory;
////    public ChatResponse recordHistory(final String chatRoomNo, final ChatRequest request) {
////
////        final ChatMessage save = chatRespitory.save(
////                ChatHistory.of(Long.parseLong(chatRoomNo), request.sender(), request.senderUuid(),
////                        request.msg(), request.imgUrl())
////        );
////
////        return ChatResponse.of(save);
////    }
////
////    public List<ChatMessage> readHistory(final long chatRoomId, final String chatIdx,
////                                         final int size) {
////
////        return chatRepository.findAllCursorPagingBy(chatRoomId, chatIdx, size);
////    }
//}