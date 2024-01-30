package com.pjg.secreto.board.command.service;

import com.pjg.secreto.board.command.dto.UpdateBoardRequestDto;
import com.pjg.secreto.board.command.dto.WriteReplyRequestDto;
import com.pjg.secreto.board.command.dto.WriterBoardRequestDto;
import com.pjg.secreto.board.command.repository.BoardCommandRepository;
import com.pjg.secreto.board.command.repository.LikedCommandRepository;
import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardCategory;
import com.pjg.secreto.board.common.entity.Liked;
import com.pjg.secreto.board.query.repository.BoardQueryRepository;
import com.pjg.secreto.board.query.repository.LikedQueryRepository;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class BoardCommandServiceImpl implements BoardCommandService {
    private BoardQueryRepository boardQueryRepository;
    private BoardCommandRepository boardCommandRepository;
    private LikedQueryRepository likedQueryRepository;
    private LikedCommandRepository likedCommandRepository;
    private RoomUserQueryRepository roomUserQueryRepository;

    @Override
    public Long updatePost(UpdateBoardRequestDto updateBoardRequestDto) {
        Long boardNo = updateBoardRequestDto.getBoardNo();
        BoardCategory newCategory = BoardCategory.valueOf(updateBoardRequestDto.getBoardCategory());
        String newTitle = updateBoardRequestDto.getTitle();
        String newContent = updateBoardRequestDto.getContent();
        Boolean newPublicYn = updateBoardRequestDto.getPublicYn();

        Optional<Board> post = Optional.ofNullable(boardQueryRepository.getById(boardNo));
        post.ifPresent(selectBoard->{
            selectBoard.updateBoard(boardNo, newTitle, newContent, String.valueOf(newCategory), newPublicYn);
        });

        return boardNo;
    }

    @Override
    public void deletePost(Long boardNo) {
        Board board = boardQueryRepository.findById(boardNo)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+boardNo));
        boardCommandRepository.delete(board);
    }

    @Override
    public void writePost(WriterBoardRequestDto writerBoardRequestDto) {
        Board board = writerBoardRequestDto.toEntity();
        boardCommandRepository.save(board);
    }

    @Override
    public void updateLike(Long boardNo, Long roomUserNo) {
        Board board = boardQueryRepository.findById(boardNo)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+boardNo));

        RoomUser roomUser = roomUserQueryRepository.findById(roomUserNo)
                .orElseThrow(()->new IllegalArgumentException("유저가 없습니다. id="+roomUserNo));

        likedCommandRepository.save(new Liked(board, roomUser));

    }

    @Override
    public void deleteLike(Long boardNo, Long roomUserNo) {
        Board board = boardQueryRepository.findById(boardNo)
                .orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+boardNo));

        RoomUser roomUser = roomUserQueryRepository.findById(roomUserNo)
                .orElseThrow(()->new IllegalArgumentException("유저가 없습니다. id="+roomUserNo));

        Liked liked = likedQueryRepository.findLikedByBoardAndRoomUser(board, roomUser)
                .orElseThrow(()->new IllegalArgumentException("이미 처리된 요청입니다."));

        likedCommandRepository.delete(liked);
    }

    @Override
    public void writeReply(WriteReplyRequestDto writeReplyRequestDto) {

    }

    @Override
    public void deleteReply(Long replyNo) {

    }
}
