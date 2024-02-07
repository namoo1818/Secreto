package com.pjg.secreto.board.query.service;

import com.pjg.secreto.board.command.repository.BoardCommandRepository;
import com.pjg.secreto.board.command.repository.BoardEntryLogCommandRepository;
import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardCategory;
import com.pjg.secreto.board.common.entity.BoardEntryLog;
import com.pjg.secreto.board.common.entity.Reply;
import com.pjg.secreto.board.common.exception.BoardException;
import com.pjg.secreto.board.query.dto.SearchBoardRequestDto;
import com.pjg.secreto.board.query.dto.SearchBoardResponseDto;
import com.pjg.secreto.board.query.dto.SearchPostResponseDto;
import com.pjg.secreto.board.query.dto.SearchReplyResponseDto;
import com.pjg.secreto.board.query.repository.BoardEntryLogQueryRepository;
import com.pjg.secreto.board.query.repository.BoardQueryRepository;
import com.pjg.secreto.board.query.repository.ReplyQueryRepository;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import com.pjg.secreto.user.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardQueryServiceImpl implements BoardQueryService{
    private BoardEntryLogCommandRepository boardEntryLogCommandRepository;
    private BoardEntryLogQueryRepository boardEntryLogQueryRepository;
    private BoardCommandRepository boardCommandRepository;
    private BoardQueryRepository boardQueryRepository;
    private ReplyQueryRepository replyQueryRepository;
    private RoomUserQueryRepository roomUserQueryRepository;

    @Autowired
    public BoardQueryServiceImpl(BoardEntryLogCommandRepository boardEntryLogCommandRepository, BoardEntryLogQueryRepository boardEntryLogQueryRepository, BoardCommandRepository boardCommandRepository, BoardQueryRepository boardQueryRepository, ReplyQueryRepository replyQueryRepository, RoomUserQueryRepository roomUserQueryRepository){
        this.boardEntryLogCommandRepository = boardEntryLogCommandRepository;
        this.boardEntryLogQueryRepository = boardEntryLogQueryRepository;
        this.boardCommandRepository = boardCommandRepository;
        this.boardQueryRepository = boardQueryRepository;
        this.replyQueryRepository = replyQueryRepository;
        this.roomUserQueryRepository = roomUserQueryRepository;
    }

    @Override
    public Page<SearchBoardResponseDto> getBoardBySpecification(Long roomNo, Long userNo, SearchBoardRequestDto serachRequest, Pageable pageable) {

        try {
            RoomUser roomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, roomNo).orElseThrow(() -> new BoardException("방에 참여한 유저가 아닙니다."));
            BoardCategory boardCategory = serachRequest.getBoardCategory();
            String title = serachRequest.getTitle();
            String content = serachRequest.getContent();
            String writer = serachRequest.getWriter();

            Page<Board> boardPage;

            if (boardCategory == null) {
                new BoardException("게시판 카테고리를 선택해주세요");
            }
            if (title != null) {
                boardPage =   boardQueryRepository.findBytitle(serachRequest.getBoardCategory(), serachRequest.getTitle() ,roomUser.getRoom().getId(), pageable);
            } else if (content != null) {
                boardPage =  boardQueryRepository.findBycontent(serachRequest.getBoardCategory(), serachRequest.getContent() ,roomUser.getRoom().getId(),pageable);
            } else if (writer != null) {
                boardPage =  boardQueryRepository.findBywriter(serachRequest.getBoardCategory(), serachRequest.getWriter() ,roomUser.getRoom().getId(),pageable);
            } else {
                boardPage =  boardQueryRepository.findByBoardCategory(serachRequest.getBoardCategory() ,roomUser.getRoom().getId(), pageable);
            }

            return boardPage.map(board -> SearchBoardResponseDto.builder()
                    .boardNo(board.getId())
                    .title(board.getTitle())
                    .registerAt(board.getRegisterAt())
                    .hit(board.getHit())
                    .boardCategory(board.getBoardCategory())
                    .publicYn(board.getPublicYn())
                    .missionCategory(board.getUserMission() != null ? board.getUserMission().getContent() : null)
                    .likedCount(board.getLikedCount())
                    .writer(board.getWriter())
                    .writerEmail(board.getRoomUser().getUser().getEmail())
                    .writerProfileUrl(board.getRoomUser().getUser().getProfileUrl())
                    .replyCount((int) board.getReplies().stream().filter(reply -> !reply.getDeleteYn())
                            .count())
                    .imgUrl(board.getImgUrl())
                    .build());
        }catch (Exception e){
            throw new BoardException(e.getMessage());
        }
    }

    @Override
    public SearchPostResponseDto getPost(Long boardNo, Long roomNo, Long userNo) {
        try {
            RoomUser roomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, roomNo).orElseThrow(() -> new BoardException("방에 참여한 유저가 아닙니다."));
            Board board = boardQueryRepository.findById(boardNo).orElseThrow();
            Optional boardEntryLog = boardEntryLogQueryRepository.findByBoardAndAndRoomUserAndAndEntryAt(board, roomUser, LocalDate.now());

            if (boardEntryLog.isEmpty()) {
                boardEntryLogCommandRepository.save(new BoardEntryLog(board, roomUser, LocalDate.now()));
                board.updateHit(board.getHit() + 1);
            }

            User user = board.getRoomUser().getUser();

            String writerEmail = user.getEmail();
            String writerProfileUrl = user.getProfileUrl();

            Boolean publicYn = board.getPublicYn();

            if (!publicYn && !board.getRoomUser().equals(roomUser)) {
                throw new BoardException("비공개 게시글입니다.");
            }

            SearchPostResponseDto searchPostResponseDto = SearchPostResponseDto.builder()
                    .boardNo(board.getId())
                    .roomUserNo(board.getRoomUser().getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .registerAt(board.getRegisterAt())
                    .hit(board.getHit())
                    .boardCategory(board.getBoardCategory())
                    .publicYn(board.getPublicYn())
                    .missionCategory(board.getUserMission() != null ? board.getUserMission().getContent() : null)
                    .likedCount(board.getLikedCount())
                    .writer(board.getWriter())
                    .writerEmail(writerEmail)
                    .writerProfileUrl(writerProfileUrl)
                    .build();
            return searchPostResponseDto;
        } catch (Exception e){
            throw new BoardException(e.getMessage());
        }
    }


    @Override
    public List<SearchReplyResponseDto> getRely(Long boardNo, Long roomNo, Long userNo) {
        try {

            RoomUser roomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, roomNo).orElseThrow(()->new BoardException("방에 참여한 유저가 아닙니다."));

            Board board = boardQueryRepository.getById(boardNo);

            if(!board.getRoomUser().getRoom().equals(roomUser.getRoom())) {
                throw new BoardException("댓글을 조회할 권한이 없습니다.");
            }

            List<Reply> replyList = replyQueryRepository.findAllByBoard(board);

            List<SearchReplyResponseDto> searchReplyResponseDtoList = new ArrayList<>();

            for (Reply reply : replyList) {
                RoomUser ru = roomUserQueryRepository.findById(reply.getRoomUser().getId()).orElseThrow();
                User user = ru.getUser();

                String writerEmail = user.getEmail();
                String writerProfileUrl = user.getProfileUrl();

                Boolean anonymityYn = reply.getAnonymityYn();
                if (anonymityYn) {
                    writerEmail = null;
                    writerProfileUrl = null;
                }

                Long tagUserNo = reply.getTagUserNo();
                String tagUserNickname = null;

                if (tagUserNo != null) {
                    RoomUser tagUser = roomUserQueryRepository.findById(tagUserNo).orElseThrow(
                            () -> new BoardException("태그한 유저가 없습니다. id=" + tagUserNo)
                    );

                    tagUserNickname = tagUser.getNickname();
                }

                SearchReplyResponseDto responseDto;

                if(!reply.getDeleteYn()) {
                    responseDto = SearchReplyResponseDto.builder()
                            .replyNo(reply.getId())
                            .roomUserNo(reply.getRoomUser().getId())
                            .content(reply.getContent())
                            .registerAt(reply.getRegisterAt())
                            .parentReplyNo(reply.getParentReplyNo())
                            .tagUserNickname(tagUserNickname)
                            .writer(reply.getWriter())
                            .writerEmail(writerEmail)
                            .writerProfileUrl(writerProfileUrl)
                            .deleteYn(reply.getDeleteYn())
                            .build();

                } else{
                    responseDto = SearchReplyResponseDto.builder()
                            .replyNo(reply.getId())
                            .parentReplyNo(reply.getParentReplyNo())
                            .deleteYn(reply.getDeleteYn())
                            .build();
                }
                searchReplyResponseDtoList.add(responseDto);
            }

            return searchReplyResponseDtoList;
        }catch(Exception e){
            throw new BoardException(e.getMessage());
        }
    }
}
