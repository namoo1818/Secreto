package com.pjg.secreto.mission.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MissionException extends RuntimeException{

    public MissionException(String msg) {
        super(msg);
    }
}
