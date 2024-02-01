package com.pjg.secreto.board.query.service;

import com.pjg.secreto.board.query.dto.SearchBoardRequestDto;
import com.pjg.secreto.board.query.dto.SearchBoardResponseDto;
import com.pjg.secreto.board.query.dto.SearchPostResponseDto;
import com.pjg.secreto.board.query.dto.SearchReplyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardQueryService {
    public Page<SearchBoardResponseDto> getBoardBySpecification(SearchBoardRequestDto serachRequest, Pageable pageable);

    public SearchPostResponseDto getPost(Long boardNo, Long roomUserNo);

    public List<SearchReplyResponseDto> getRely(Long boardNo);

}
