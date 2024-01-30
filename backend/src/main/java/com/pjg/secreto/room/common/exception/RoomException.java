package com.pjg.secreto.room.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RoomException extends RuntimeException {

    public RoomException(String msg) {

        super(msg);
    }
}
