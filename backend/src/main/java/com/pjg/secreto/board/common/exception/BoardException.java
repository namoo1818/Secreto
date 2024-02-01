package com.pjg.secreto.board.common.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BoardException extends RuntimeException {
    public BoardException(String msg) {

        super(msg);
    }
}
