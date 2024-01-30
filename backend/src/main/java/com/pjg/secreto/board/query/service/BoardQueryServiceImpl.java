package com.pjg.secreto.board.query.service;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.Reply;
import com.pjg.secreto.board.query.dto.SearchBoardRequestDto;
import com.pjg.secreto.board.query.dto.SearchBoardResponseDto;
import com.pjg.secreto.board.query.dto.SearchPostResponseDto;
import com.pjg.secreto.board.query.dto.SearchReplyResponseDto;
import com.pjg.secreto.board.query.repository.BoardQueryRepository;
import com.pjg.secreto.board.query.repository.BoardSpecification;
import com.pjg.secreto.board.query.repository.ReplyQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardQueryServiceImpl implements BoardQueryService{
    private BoardQueryRepository boardQueryRepository;
    private ReplyQueryRepository replyQueryRepository;

    @Autowired
    public BoardQueryServiceImpl(BoardQueryRepository boardQueryRepository, ReplyQueryRepository replyQueryRepository){
        this.boardQueryRepository = boardQueryRepository;
        this.replyQueryRepository = replyQueryRepository;
    }

    @Override
    public Page<SearchBoardResponseDto> getBoardBySpecification(SearchBoardRequestDto serachRequest, Pageable pageable) {
        Specification<Board> spec = (root, query, criteriaBuilder) -> null;

        Long roomUserNo = serachRequest.getRoomUserNo();
        String boardCategory = serachRequest.getBoardCategory();

        if(serachRequest.getTitle()!=null){
            spec = spec.and(BoardSpecification.boardRoomUserAndCategoryAndTitle(
                    roomUserNo,
                    boardCategory,
                    serachRequest.getTitle()
            ));
        }
        if(serachRequest.getContent()!=null){
            spec = spec.and(BoardSpecification.boardRoomUserAndCategoryAndContent(
                    roomUserNo,
                    boardCategory,
                    serachRequest.getContent()
            ));
        }
        if(serachRequest.getWriter()!=null){
            spec = spec.and(BoardSpecification.boardRoomUserAndCategoryAndWriter(
                    roomUserNo,
                    boardCategory,
                    serachRequest.getWriter()
            ));
        }

        Page<SearchBoardResponseDto> searchBoardResponseDto = boardQueryRepository.findAll(spec, pageable).map(SearchBoardResponseDto::toDto);

        return searchBoardResponseDto;
    }

    @Override
    public SearchPostResponseDto getPost(Long boardNo) {
        Board board = boardQueryRepository.findById(boardNo).orElseThrow();
        return SearchPostResponseDto.toDto(board);
    }


    @Override
    public List<SearchReplyResponseDto> getRely(Long boardNo) {
        Board board = boardQueryRepository.getById(boardNo);

        List<Reply> replyList = replyQueryRepository.findAllByBoard(board);

        List<SearchReplyResponseDto> searchReplyResponseDtoList = new ArrayList<>();

        for(Reply reply:replyList){
            searchReplyResponseDtoList.add(SearchReplyResponseDto.toDto(reply));
        }

        return searchReplyResponseDtoList;
    }
}
