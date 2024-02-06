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
import com.pjg.secreto.user.common.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

            List<Board> boardList;

            if (boardCategory == null) {
                throw new BoardException("게시판 카테고리를 선택해주세요");
            }
            if (title != null) {
                boardList = boardQueryRepository.findBoardByBoardCategoryAndTitleContaining(boardCategory, title);
            } else if (content != null) {
                boardList = boardQueryRepository.findBoardByBoardCategoryAndContentContaining(boardCategory, content);
            } else if (writer != null) {
                boardList = boardQueryRepository.findBoardByBoardCategoryAndWriterContaining(boardCategory, writer);
            } else {
                boardList = boardQueryRepository.findBoardByBoardCategory(boardCategory);
            }

            List<SearchBoardResponseDto> boardResponseDtoList = new ArrayList<>();

            for (Board board : boardList) {
                if (board.getRoomUser().getRoom().getId() == roomNo) {
                    User user = board.getRoomUser().getUser();

                    String writerEmail = user.getEmail();
                    String writerProfileUrl = user.getProfileUrl();

                    Boolean publicYn = board.getPublicYn();

                    if (publicYn || (!publicYn && board.getRoomUser().equals(roomUser))) {

                        SearchBoardResponseDto searchBoardResponseDto = SearchBoardResponseDto.builder()
                                .boardNo(board.getId())
                                .title(board.getTitle())
                                .registerAt(board.getRegisterAt())
                                .hit(board.getHit())
                                .boardCategory(board.getBoardCategory())
                                .publicYn(board.getPublicYn())
                                .missionCategory(board.getMissionCategory())
                                .likedCount(board.getLikedCount())
                                .writer(board.getWriter())
                                .writerEmail(writerEmail)
                                .writerProfileUrl(writerProfileUrl)
                                .replyCount(board.getReplies().size())
                                .imgUrl(board.getImgUrl())
                                .build();
                        boardResponseDtoList.add(searchBoardResponseDto);
                    }
                }
            }

            int size = pageable.getPageSize();
            int start = (int) pageable.getOffset();
            int end = Math.min((start + size), boardResponseDtoList.size());

            Page<SearchBoardResponseDto> searchBoardResponseDto = new PageImpl<>(boardResponseDtoList.subList(start, end), pageable, boardResponseDtoList.size());

            return searchBoardResponseDto;
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
                    .missionCategory(board.getMissionCategory())
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

            List<Reply> replyList = replyQueryRepository.findAllByBoard(board);

            List<SearchReplyResponseDto> searchReplyResponseDtoList = new ArrayList<>();

            for (Reply reply : replyList) {
                RoomUser ru = roomUserQueryRepository.findById(reply.getRoomUser().getId()).orElseThrow();
                User user = ru.getUser();

                String writerEmail = user.getEmail();
                String writerProfileUrl = user.getProfileUrl();

                Boolean annonymityYn = reply.getAnonymityYn();
                if (annonymityYn) {
                    writerEmail = null;
                    writerProfileUrl = null;
                }

                Long tagUserNo = reply.getTagUserNo();
                String tagUserNickname = null;

                if (tagUserNo != null) {
                    RoomUser tagUser = roomUserQueryRepository.findById(tagUserNo).orElseThrow(
                            () -> new UserException("태그한 유저가 없습니다. id=" + tagUserNo)
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
            throw new UserException(e.getMessage());
        }
    }
}
