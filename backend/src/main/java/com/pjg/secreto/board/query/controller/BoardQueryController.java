package com.pjg.secreto.board.query.controller;

import com.pjg.secreto.board.query.dto.SearchBoardRequestDto;
import com.pjg.secreto.board.query.dto.SearchBoardResponseDto;
import com.pjg.secreto.board.query.dto.SearchPostResponseDto;
import com.pjg.secreto.board.query.dto.SearchReplyResponseDto;
import com.pjg.secreto.board.query.service.BoardQueryService;
import com.pjg.secreto.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardQueryController {

    private final BoardQueryService boardQueryService;

    // 게시판 카테고리에 속한 모든 게시글 조회
    @GetMapping(value="/board")
    public ResponseEntity<?> readBoard(
            @ModelAttribute("searchRequest") SearchBoardRequestDto searchRequest,
            @PageableDefault(size = 5, sort = "registerAt", direction = Sort.Direction.DESC) Pageable pageable){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        Page<SearchBoardResponseDto> result = boardQueryService.getBoardBySpecification(searchRequest, pageable);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 전체 조회 성공", result));
    }

    // 게시글 상세 조회
    @GetMapping(value="/post/{boardNo}")
    public ResponseEntity<?> readPost(@PathVariable Long boardNo){
        Long roomUserNo = 1L;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        SearchPostResponseDto result = boardQueryService.getPost(boardNo, roomUserNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 상세 조회 성공", result));
    }

    // 게시판에 속한 모든 댓글 조회
    @GetMapping(value="reply/{boardNo}")
    public ResponseEntity<?> readReply(@PathVariable Long boardNo){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        List<SearchReplyResponseDto> result = boardQueryService.getRely(boardNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "댓글 조회 성공", result));
    }
}
