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
    public Page<SearchBoardResponseDto> getBoardBySpecification(SearchBoardRequestDto serachRequest, Pageable pageable) {
        Long roomNo = serachRequest.getRoomNo();
        BoardCategory boardCategory = serachRequest.getBoardCategory();
        String title = serachRequest.getTitle();
        String content = serachRequest.getContent();
        String writer = serachRequest.getWriter();

        List<Board> boardList;

        if(boardCategory==null) {
            throw new BoardException("게시판 카테고리를 선택해주세요");
        }
        if(title!=null) {
            boardList = boardQueryRepository.findBoardByBoardCategoryAndTitleContaining(boardCategory, title);
        }
        else if(content!=null){
            boardList = boardQueryRepository.findBoardByBoardCategoryAndContentContaining(boardCategory, content);
        }
        else if(writer!=null){
            boardList = boardQueryRepository.findBoardByBoardCategoryAndWriterContaining(boardCategory, writer);
        } else {
            boardList = boardQueryRepository.findBoardByBoardCategory(boardCategory);
        }

        List<SearchBoardResponseDto> boardResponseDtoList = new ArrayList<>();

        for(Board board:boardList){
            if(board.getRoomUser().getRoom().getId()==roomNo) {
                User user = board.getRoomUser().getUser();

                String writerEmail = user.getEmail();
                String writerProfileUrl = user.getProfileUrl();

                Boolean publicYn = board.getPublicYn();

                if (!publicYn) {
                    writerEmail = null;
                    writerProfileUrl = null;
                }

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

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int start = (int)pageable.getOffset();
        int end = Math.min((start+size),boardResponseDtoList.size());

        Page<SearchBoardResponseDto> searchBoardResponseDto = new PageImpl<>(boardResponseDtoList.subList(start, end), pageable, boardResponseDtoList.size());

        return searchBoardResponseDto;
    }

    @Override
    public SearchPostResponseDto getPost(Long boardNo, Long roomUserNo) {
        Board board = boardQueryRepository.findById(boardNo).orElseThrow();
        RoomUser roomUser = roomUserQueryRepository.findById(roomUserNo).orElseThrow();

        Optional boardEntryLog = boardEntryLogQueryRepository.findByBoardAndAndRoomUserAndAndEntryAt(board, roomUser, LocalDate.now());

        if(boardEntryLog.isEmpty()){
            boardEntryLogCommandRepository.save(new BoardEntryLog(board, roomUser, LocalDate.now()));
            board.updateHit(board.getHit()+1);
        }

        User user = board.getRoomUser().getUser();

        String writerEmail = user.getEmail();
        String writerProfileUrl = user.getProfileUrl();

        Boolean publicYn = board.getPublicYn();

        if(!publicYn){
            writerEmail = null;
            writerProfileUrl = null;
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
    }


    @Override
    public List<SearchReplyResponseDto> getRely(Long boardNo) {
        Board board = boardQueryRepository.getById(boardNo);

        List<Reply> replyList = replyQueryRepository.findAllByBoard(board);

        List<SearchReplyResponseDto> searchReplyResponseDtoList = new ArrayList<>();

        for(Reply reply:replyList){
            RoomUser roomUser = roomUserQueryRepository.findById(reply.getRoomUser().getId()).orElseThrow();
            User user = roomUser.getUser();

            String writerEmail = user.getEmail();
            String writerProfileUrl = user.getProfileUrl();

            Boolean annonymityYn = reply.getAnnonymityYn();
            if(annonymityYn){
                writerEmail = null;
                writerProfileUrl = null;
            }

            SearchReplyResponseDto responseDto =  SearchReplyResponseDto.builder()
                    .replyNo(reply.getId())
                    .roomUserNo(reply.getRoomUser().getId())
                    .content(reply.getContent())
                    .registerAt(reply.getRegisterAt())
                    .parentReplyNo(reply.getParentReplyNo())
                    .tagUserNo(reply.getTagUserNo())
                    .writer(reply.getWriter())
                    .writerEmail(writerEmail)
                    .writerProfileUrl(writerProfileUrl)
                    .build();
            searchReplyResponseDtoList.add(responseDto);
        }

        return searchReplyResponseDtoList;
    }
}
