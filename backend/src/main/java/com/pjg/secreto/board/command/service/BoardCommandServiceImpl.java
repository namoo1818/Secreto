package com.pjg.secreto.board.command.service;

import com.pjg.secreto.board.command.dto.UpdateBoardRequestDto;
import com.pjg.secreto.board.command.dto.WriteBoardRequestDto;
import com.pjg.secreto.board.command.dto.WriteReplyRequestDto;
import com.pjg.secreto.board.command.repository.BoardCommandRepository;
import com.pjg.secreto.board.command.repository.LikedCommandRepository;
import com.pjg.secreto.board.command.repository.ReplyCommandRepository;
import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardCategory;
import com.pjg.secreto.board.common.entity.Liked;
import com.pjg.secreto.board.common.entity.Reply;
import com.pjg.secreto.board.common.exception.BoardException;
import com.pjg.secreto.board.query.repository.BoardQueryRepository;
import com.pjg.secreto.board.query.repository.LikedQueryRepository;
import com.pjg.secreto.board.query.repository.ReplyQueryRepository;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import com.pjg.secreto.user.common.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
@RequiredArgsConstructor
@Transactional
@Service
public class BoardCommandServiceImpl implements BoardCommandService {
    private BoardQueryRepository boardQueryRepository;
    private BoardCommandRepository boardCommandRepository;
    private ReplyQueryRepository replyQueryRepository;
    private ReplyCommandRepository replyCommandRepository;
    private LikedQueryRepository likedQueryRepository;
    private LikedCommandRepository likedCommandRepository;
    private RoomUserQueryRepository roomUserQueryRepository;

    @Autowired
    public BoardCommandServiceImpl(BoardQueryRepository boardQueryRepository, BoardCommandRepository boardCommandRepository, ReplyQueryRepository replyQueryRepository, ReplyCommandRepository replyCommandRepository, LikedQueryRepository likedQueryRepository, LikedCommandRepository likedCommandRepository, RoomUserQueryRepository roomUserQueryRepository) {
        this.boardQueryRepository = boardQueryRepository;
        this.boardCommandRepository = boardCommandRepository;
        this.replyQueryRepository = replyQueryRepository;
        this.replyCommandRepository = replyCommandRepository;
        this.likedQueryRepository = likedQueryRepository;
        this.likedCommandRepository = likedCommandRepository;
        this.roomUserQueryRepository = roomUserQueryRepository;
    }

    @Override
    public Long updatePost(UpdateBoardRequestDto updateBoardRequestDto) {
        Long boardNo = updateBoardRequestDto.getBoardNo();
        BoardCategory newCategory = updateBoardRequestDto.getBoardCategory();
        String newTitle = updateBoardRequestDto.getTitle();
        String newContent = updateBoardRequestDto.getContent();
        String newImgUrl = updateBoardRequestDto.getImgUrl();
        Boolean newPublicYn = updateBoardRequestDto.getPublicYn();

        Optional<Board> post = boardQueryRepository.findById(boardNo);
        post.ifPresent(selectBoard->{
            selectBoard.updateBoard(boardNo, newTitle, newContent, newImgUrl, newCategory, newPublicYn);
        });

        return boardNo;
    }

    @Override
    public void deletePost(Long boardNo) {
        Board board = boardQueryRepository.findById(boardNo)
                .orElseThrow(()->new BoardException("해당 게시글이 없습니다. id="+boardNo));
        boardCommandRepository.delete(board);
    }

    @Override
    public void writePost(WriteBoardRequestDto writeBoardRequestDto) {
        Long roomUserNo = writeBoardRequestDto.getRoomUserNo();
        RoomUser roomUser = roomUserQueryRepository.findById(roomUserNo).orElseThrow(
                () -> new BoardException()
        );


        Board board = Board.builder()
                .roomUser(roomUser)
                .title(writeBoardRequestDto.getTitle())
                .content(writeBoardRequestDto.getContent())
                .imgUrl(writeBoardRequestDto.getImgUrl())
                .registerAt(LocalDateTime.now())
                .boardCategory(writeBoardRequestDto.getBoardCategory())
                .publicYn(writeBoardRequestDto.getPublicYn())
                .missionCategory(writeBoardRequestDto.getMissionCategory())
                .writer("writer")   // 추후 수정
                .hit(0L)
                .likedCount(0L)
                .build();
        boardCommandRepository.save(board);
    }

    @Override
    public void updateLike(Long boardNo, Long roomUserNo) {
        try {
            Board board = boardQueryRepository.findById(boardNo)
                    .orElseThrow(() -> new BoardException("해당 게시글이 없습니다. id=" + boardNo));

            RoomUser roomUser = roomUserQueryRepository.findById(roomUserNo)
                    .orElseThrow(() -> new BoardException("유저가 없습니다. id=" + roomUserNo));

            likedQueryRepository.findLikedByBoardAndRoomUser(board, roomUser)
                    .ifPresent(a -> {
                        throw new BoardException("이미 좋아요를 눌렀습니다.");
                    });

            likedCommandRepository.save(new Liked(board, roomUser));
            board.updateLikedCount(board.getLikedCount() + 1);
        } catch (Exception e) {
            throw new BoardException(e.getMessage());
        }

    }

    @Override
    public void deleteLike(Long boardNo, Long roomUserNo) {
        Board board = boardQueryRepository.findById(boardNo)
                .orElseThrow(()->new BoardException("해당 게시글이 없습니다. id="+boardNo));

        RoomUser roomUser = roomUserQueryRepository.findById(roomUserNo)
                .orElseThrow(()->new BoardException("유저가 없습니다. id="+roomUserNo));

        Liked liked = likedQueryRepository.findLikedByBoardAndRoomUser(board, roomUser)
                .orElseThrow(()->new BoardException("이미 처리된 요청입니다."));

        likedCommandRepository.delete(liked);

        board.updateLikedCount(board.getLikedCount()-1);
    }

    @Override
    public void writeReply(WriteReplyRequestDto writeReplyRequestDto) {
        Long roomUserNo = writeReplyRequestDto.getRoomUserNo();
        RoomUser roomUser = roomUserQueryRepository.findById(roomUserNo)
                .orElseThrow(()->new UserException("해당 유저가 없습니다. id="+roomUserNo));

        Long boardNo = writeReplyRequestDto.getBoardNo();
        Board board = boardQueryRepository.findById(boardNo)
                .orElseThrow(()->new BoardException("해당 게시글이 없습니다. id="+boardNo));

        Boolean annonymityYn = writeReplyRequestDto.getAnnonymityYn();
        String writer = "";

        if(annonymityYn){
            writer = "익명";
        } else {
            writer = roomUser.getNickname();
        }

        Reply reply = Reply.builder()
                .roomUser(roomUser)
                .board(board)
                .content(writeReplyRequestDto.getContnet())
                .registerAt(LocalDateTime.now())
                .parentReplyNo(writeReplyRequestDto.getParentReplyNo())
                .tagUserNo(writeReplyRequestDto.getTagUserNo())
                .writer(writer)
                .annonymityYn(writeReplyRequestDto.getAnnonymityYn())
                .build();

        replyCommandRepository.save(reply);
    }

    @Override
    public void deleteReply(Long replyNo) {

        Reply reply = replyQueryRepository.findById(replyNo)
                .orElseThrow(()->new BoardException("해당 댓글이 없습니다. id="+replyNo));
        replyCommandRepository.delete(reply);
                
    }
}
