package com.pjg.secreto.board.command.controller;

import com.pjg.secreto.board.command.dto.UpdateBoardRequestDto;
import com.pjg.secreto.board.command.dto.UpdateReplyRequestDto;
import com.pjg.secreto.board.command.dto.WriteBoardRequestDto;
import com.pjg.secreto.board.command.dto.WriteReplyRequestDto;
import com.pjg.secreto.board.command.service.BoardCommandService;
import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BoardCommandController {

    private final BoardCommandService boardCommandService;

    // 게시판 수정
    @PutMapping(value="/post/{boardNo}/room/{roomNo}")
    public ResponseEntity<?> updatePost(@PathVariable("boardNo")Long boardNo, @PathVariable("roomNo")Long roomNo, @RequestBody UpdateBoardRequestDto updateBoardRequestDto){
        Long userNo = AuthUtils.getAuthenticatedUserId();

        Long result = boardCommandService.updatePost(boardNo, roomNo, userNo, updateBoardRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 수정 성공", result));
    }

    // 게시글 삭제
    @DeleteMapping(value="/post/{boardNo}/room/{roomNo}")
    public ResponseEntity<?> deletePost(@PathVariable("boardNo")Long boardNo, @PathVariable("roomNo")Long roomNo){

        Long userNo = AuthUtils.getAuthenticatedUserId();

        boardCommandService.deletePost(roomNo, userNo, boardNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 삭제 성공"));
    }

    // 게시글 작성
    @PostMapping(value="/post/{roomNo}")
    public ResponseEntity<?> createPost(@PathVariable Long roomNo, @RequestBody WriteBoardRequestDto writeBoardRequestDto){

        Long userNo = AuthUtils.getAuthenticatedUserId();

        Long result = boardCommandService.writePost(roomNo, userNo, writeBoardRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 작성 성공", result));
    }

    // 게시글에 좋아요 
    @PostMapping(value="/post/{boardNo}/like/room/{roomNo}")
    public ResponseEntity<?> likePost(@PathVariable("boardNo") Long boardNo, @PathVariable("roomNo")Long roomNo){
        Long userNo = AuthUtils.getAuthenticatedUserId();

        boardCommandService.updateLike(roomNo, userNo, boardNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "좋아요 성공"));
    }

    // 게시글에 좋아요 취소
    @DeleteMapping(value="/post/{boardNo}/unlike/room/{roomNo}")
    public ResponseEntity<?> unlikePost(@PathVariable("boardNo") Long boardNo, @PathVariable("roomNo")Long roomNo){
        Long userNo = AuthUtils.getAuthenticatedUserId();

        boardCommandService.deleteLike( roomNo, userNo, boardNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "좋아요 취소 성공"));
    }

    // 댓글 작성
    @PostMapping(value="/reply/{boardNo}/room/{roomNo}")
    public ResponseEntity<?> createReply(@PathVariable("boardNo") Long boardNo, @PathVariable("roomNo") Long roomNo, @RequestBody WriteReplyRequestDto writeReplyRequestDto){
        Long userNo = AuthUtils.getAuthenticatedUserId();

        boardCommandService.writeReply(boardNo,  roomNo, userNo, writeReplyRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "댓글 작성 성공"));
    }

    // 댓글 삭제
    @DeleteMapping(value="/reply/{replyNo}/room/{roomNo}")
    public ResponseEntity<?> deleteReply(@PathVariable("replyNo") Long replyNo, @PathVariable("roomNo")Long roomNo){
        Long userNo = AuthUtils.getAuthenticatedUserId();

        boardCommandService.deleteReply(roomNo, userNo, replyNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "댓글 삭제 성공"));
    }

    //댓글 수정
    @PutMapping(value="/reply/{replyNo}/room/{roomNo}")
    public ResponseEntity<?> updateRely(@PathVariable("replyNo")Long replyNo, @PathVariable("roomNo")Long roomNo, @RequestBody UpdateReplyRequestDto updateReplyRequestDto){

        Long userNo = AuthUtils.getAuthenticatedUserId();

        Long result = boardCommandService.updateReply( roomNo, userNo, replyNo, updateReplyRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 수정 성공", result));
    }
}
