package com.pjg.secreto.board.command.controller;

import com.pjg.secreto.board.command.dto.UpdateBoardRequestDto;
import com.pjg.secreto.board.command.dto.WriteReplyRequestDto;
import com.pjg.secreto.board.command.dto.WriteBoardRequestDto;
import com.pjg.secreto.board.command.service.BoardCommandService;
import com.pjg.secreto.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@RestController
public class BoardCommandController {

    private final BoardCommandService boardCommandService;

    // 게시판 수정
    @PutMapping(value="/post")
    public ResponseEntity<?> updatePost(@RequestBody UpdateBoardRequestDto updateBoardRequestDto){

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Long result = boardCommandService.updatePost(updateBoardRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 수정 성공", result));
    }

    // 게시글 삭제
    @DeleteMapping(value="/post/{boardNo}")
    public ResponseEntity<?> deletePost(@PathVariable Long boardNo){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        boardCommandService.deletePost(boardNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 삭제 성공"));
    }

    // 게시글 작성
    @PostMapping(value="/post")
    public ResponseEntity<?> createPost(@RequestBody WriteBoardRequestDto writeBoardRequestDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        boardCommandService.writePost(writeBoardRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 작성 성공"));
    }

    // 게시글에 좋아요 
    @PostMapping(value="/post/{boardNo}/like")
    public ResponseEntity<?> likePost(@PathVariable Long boardNo){
        Long roomUserNo = 1L;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        boardCommandService.updateLike(boardNo, roomUserNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "좋아요 성공"));
    }

    // 게시글에 좋아요 취소
    @DeleteMapping(value="/post/{boardNo}/unlike")
    public ResponseEntity<?> unlikePost(@PathVariable Long boardNo){
        Long roomUserNo = 1L;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        boardCommandService.deleteLike(boardNo, roomUserNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "좋아요 취소 성공"));
    }

    // 댓글 작성
    @PostMapping(value="/reply/{boardNo}")
    public ResponseEntity<?> createReply(@RequestBody WriteReplyRequestDto writeReplyRequestDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        boardCommandService.writeReply(writeReplyRequestDto);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "댓글 작성 성공"));
    }

    // 댓글 삭제
    @DeleteMapping(value="/reply/{replyNo}")
    public ResponseEntity<?> deleteReply(@PathVariable Long replyNo){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        boardCommandService.deleteReply(replyNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "댓글 삭제 성공"));
    }
}
