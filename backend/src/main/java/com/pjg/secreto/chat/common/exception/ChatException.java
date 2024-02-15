package com.pjg.secreto.chat.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChatException extends RuntimeException{

    public ChatException(String msg) {
        super(msg);
    }
}