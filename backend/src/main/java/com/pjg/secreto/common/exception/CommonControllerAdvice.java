package com.pjg.secreto.common.exception;

import com.pjg.secreto.board.common.exception.BoardException;
import com.pjg.secreto.common.response.ErrorResponse;
import com.pjg.secreto.mission.common.exception.MissionException;
import com.pjg.secreto.room.common.exception.RoomException;
import com.pjg.secreto.user.common.exception.UserException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> ExpiredJwtExceptionHandler(Exception e) {
        log.error("토큰 예외 발생", e);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, "전송한 토큰이 만료되었습니다.");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> commonExceptionHandler(Exception e) {
        log.error("예외발생", e);

        return ResponseEntity.internalServerError().body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러."));
    }

    @ExceptionHandler(RoomException.class)
    public ResponseEntity<ErrorResponse> RoomExceptionHandler(Exception e) {
        log.error("방 예외발생", e);

        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(MissionException.class)
    public ResponseEntity<ErrorResponse> MissionExceptionHandler(Exception e) {
        log.error("미션 예외발생", e);

        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> UserExceptionHandler(Exception e) {
        log.error("유저 예외 발생", e);

        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler(BoardException.class)
    public ResponseEntity<?> BoardExceptionHandler(Exception e) {
        log.error("게시판 예외 발생", e);

        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

}