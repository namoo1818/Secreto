package com.pjg.secreto.common.exception;

import com.pjg.secreto.common.response.ErrorResponse;
import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.room.common.exception.RoomException;
import com.pjg.secreto.user.common.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> commonExceptionHandler(Exception e) {
        log.error("예외발생", e);

        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러."));
    }

    @ExceptionHandler(RoomException.class)
    public ResponseEntity<ErrorResponse> RoomExceptionHandler(Exception e) {
        log.error("방 예외발생", e);

        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> UserExceptionHandler(Exception e) {
        log.error("유저 예외 발생", e);

        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }


}