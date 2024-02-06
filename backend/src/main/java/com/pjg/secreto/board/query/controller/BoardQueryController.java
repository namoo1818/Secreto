package com.pjg.secreto.board.query.controller;

import com.pjg.secreto.board.query.dto.SearchBoardRequestDto;
import com.pjg.secreto.board.query.dto.SearchBoardResponseDto;
import com.pjg.secreto.board.query.dto.SearchPostResponseDto;
import com.pjg.secreto.board.query.dto.SearchReplyResponseDto;
import com.pjg.secreto.board.query.service.BoardQueryService;
import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardQueryController {

    private final BoardQueryService boardQueryService;

    // 게시판 카테고리에 속한 모든 게시글 조회
    @GetMapping(value="/board/{roomNo}")
    public ResponseEntity<?> readBoard(
            @PathVariable Long roomNo,
            @ModelAttribute("searchRequest") SearchBoardRequestDto searchRequest,
            @PageableDefault(size = 5, sort = "registerAt", direction = Sort.Direction.DESC) Pageable pageable){
        Long userNo = AuthUtils.getAuthenticatedUserId();

        Page<SearchBoardResponseDto> result = boardQueryService.getBoardBySpecification(roomNo, userNo, searchRequest, pageable);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 전체 조회 성공", result));
    }
    // 게시판 카테고리에 속한 모든 게시글 조회
    @GetMapping(value="/board-test/{roomNo}")
    public ResponseEntity<?> readBoard2(
            @PathVariable Long roomNo,
            @ModelAttribute("searchRequest") SearchBoardRequestDto searchRequest,
            @PageableDefault(size = 5, sort = "registerAt", direction = Sort.Direction.DESC) Pageable pageable){
        Long userNo = AuthUtils.getAuthenticatedUserId();

        Page<SearchBoardResponseDto> result = boardQueryService.getBoardBySpecification(roomNo, userNo, searchRequest, pageable);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 전체 조회 성공", result));
    }

    // 게시글 상세 조회
    @GetMapping(value="/post/{boardNo}/room/{roomNo}")
    public ResponseEntity<?> readPost(@PathVariable("boardNo") Long boardNo, @PathVariable("roomNo")Long roomNo){
        Long userNo = AuthUtils.getAuthenticatedUserId();

        SearchPostResponseDto result = boardQueryService.getPost(boardNo, roomNo, userNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "게시글 상세 조회 성공", result));
    }

    // 게시판에 속한 모든 댓글 조회
    @GetMapping(value="reply/{boardNo}/room/{roomNo}")
    public ResponseEntity<?> readReply(@PathVariable("boardNo") Long boardNo, @PathVariable("roomNo")Long roomNo){
        Long userNo = AuthUtils.getAuthenticatedUserId();

        List<SearchReplyResponseDto> result = boardQueryService.getRely(boardNo, roomNo, userNo);

        return ResponseEntity.ok().body(new SuccessResponse(HttpStatus.OK, "댓글 조회 성공", result));
    }
}
