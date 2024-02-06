package com.pjg.secreto.alarm.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlarmException extends RuntimeException{

    public AlarmException(String msg) {
        super(msg);
    }
}

