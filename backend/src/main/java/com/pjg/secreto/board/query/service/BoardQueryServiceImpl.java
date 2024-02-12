package com.pjg.secreto.board.query.service;

import com.pjg.secreto.board.command.repository.BoardEntryLogCommandRepository;
import com.pjg.secreto.board.common.entity.*;
import com.pjg.secreto.board.common.exception.BoardException;
import com.pjg.secreto.board.query.dto.SearchBoardRequestDto;
import com.pjg.secreto.board.query.dto.SearchBoardResponseDto;
import com.pjg.secreto.board.query.dto.SearchPostResponseDto;
import com.pjg.secreto.board.query.dto.SearchReplyResponseDto;
import com.pjg.secreto.board.query.repository.BoardEntryLogQueryRepository;
import com.pjg.secreto.board.query.repository.BoardQueryRepository;
import com.pjg.secreto.board.query.repository.LikedQueryRepository;
import com.pjg.secreto.board.query.repository.ReplyQueryRepository;
import com.pjg.secreto.history.common.entity.Matching;
import com.pjg.secreto.history.query.repository.MatchingQueryRepository;
import com.pjg.secreto.mission.common.entity.UserMission;
import com.pjg.secreto.mission.query.repository.UserMissionQueryRepository;
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
    private BoardQueryRepository boardQueryRepository;
    private ReplyQueryRepository replyQueryRepository;
    private RoomUserQueryRepository roomUserQueryRepository;
    private MatchingQueryRepository matchingQueryRepository;
    private LikedQueryRepository likedQueryRepository;
    private UserMissionQueryRepository userMissionQueryRepository;

    @Autowired
    public BoardQueryServiceImpl(BoardEntryLogCommandRepository boardEntryLogCommandRepository, BoardEntryLogQueryRepository boardEntryLogQueryRepository,
                                 BoardQueryRepository boardQueryRepository,
                                 ReplyQueryRepository replyQueryRepository, RoomUserQueryRepository roomUserQueryRepository,
                                 MatchingQueryRepository matchingQueryRepository, LikedQueryRepository likedQueryRepository,
                                 UserMissionQueryRepository userMissionQueryRepository){
        this.boardEntryLogCommandRepository = boardEntryLogCommandRepository;
        this.boardEntryLogQueryRepository = boardEntryLogQueryRepository;
        this.boardQueryRepository = boardQueryRepository;
        this.replyQueryRepository = replyQueryRepository;
        this.roomUserQueryRepository = roomUserQueryRepository;
        this.matchingQueryRepository = matchingQueryRepository;
        this.likedQueryRepository = likedQueryRepository;
        this.userMissionQueryRepository = userMissionQueryRepository;
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
                throw new BoardException("게시판 카테고리를 선택해주세요");
            }
            if (title != null) {
                boardPage =   boardQueryRepository.findBytitle(serachRequest.getBoardCategory(), serachRequest.getTitle() ,roomUser.getRoom().getId(), roomUser.getId(), pageable);
            } else if (content != null) {
                boardPage =  boardQueryRepository.findBycontent(serachRequest.getBoardCategory(), serachRequest.getContent() ,roomUser.getRoom().getId(),roomUser.getId(),pageable);
            } else if (writer != null) {
                if(serachRequest.getBoardCategory()==BoardCategory.CERTIFICATE)
                    boardPage =  boardQueryRepository.findBymaniti(serachRequest.getBoardCategory(), serachRequest.getWriter() ,roomUser.getRoom().getId(),roomUser.getId(),pageable);
                 else
                    boardPage =  boardQueryRepository.findBywriter(serachRequest.getBoardCategory(), serachRequest.getWriter() ,roomUser.getRoom().getId(),roomUser.getId(),pageable);
            } else {
                boardPage =  boardQueryRepository.findByBoardCategory(serachRequest.getBoardCategory() ,roomUser.getRoom().getId(), roomUser.getId(),pageable);
            }

            return boardPage.map(board -> {
                SearchBoardResponseDto.SearchBoardResponseDtoBuilder builder = SearchBoardResponseDto.builder()
                        .boardNo(board.getId())
                        .title(board.getTitle())
                        .registerAt(board.getRegisterAt())
                        .hit(board.getHit())
                        .boardCategory(board.getBoardCategory())
                        .publicYn(board.getPublicYn())
                        .likedCount(board.getLikedCount())
                        .writer(board.getRoomUser().getNickname())
                        .writerEmail(board.getRoomUser().getUser().getEmail())
                        .writerProfileUrl(board.getRoomUser().getUser().getProfileUrl())
                        .replyCount((int) board.getReplies().stream().filter(reply -> !reply.getDeleteYn()).count())
                        .imgUrl(board.getImgUrl())
                        .userMissionNo(board.getUserMission()!=null?board.getUserMission().getId():null)
                        .userMission(board.getUserMission()!=null?board.getUserMission().getContent():null);

                    if(board.getBoardCategory()==BoardCategory.CERTIFICATE){
                        Long manitiNo = roomUser.getUsersManiti();
                        if(manitiNo!=null) {
                            RoomUser maniti = roomUserQueryRepository.findById(manitiNo).orElseThrow(() -> new BoardException("해당 유저가 없습니다."));
                            String manitiNickname = maniti.getNickname();
                            builder.writer(manitiNickname + "님의 마니또");
                        } else{
                            builder.writer("마니또");
                        }
                        builder.writerEmail(null);
                        builder.writerProfileUrl(null);
                    }

                    return builder.build();
        });
        }catch (Exception e){
            throw new BoardException(e.getMessage());
        }
    }

    @Override
    public SearchPostResponseDto getPost(Long boardNo, Long roomNo, Long userNo) {
        try {
            RoomUser roomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, roomNo).orElseThrow(() -> new BoardException("방에 참여한 유저가 아닙니다."));
            Board board = boardQueryRepository.findById(boardNo).orElseThrow();
            Optional<BoardEntryLog> boardEntryLog = boardEntryLogQueryRepository.findByBoardAndAndRoomUserAndAndEntryAt(board, roomUser, LocalDate.now());

            if (boardEntryLog.isEmpty()) {
                boardEntryLogCommandRepository.save(new BoardEntryLog(board, roomUser, LocalDate.now()));
                board.updateHit(board.getHit() + 1);
            }

            User user = board.getRoomUser().getUser();

            String writer = board.getRoomUser().getNickname();
            String writerEmail = user.getEmail();
            String writerProfileUrl = user.getProfileUrl();

            Boolean publicYn = board.getPublicYn();
            Boolean likedYn = false;

            if (!publicYn && !board.getRoomUser().equals(roomUser)) {
                throw new BoardException("비공개 게시글입니다.");
            }

            if(board.getBoardCategory()==BoardCategory.CERTIFICATE){
                Long manitiNo = roomUser.getUsersManiti();
                if(manitiNo!=null) {
                    RoomUser maniti = roomUserQueryRepository.findById(manitiNo).orElseThrow(() -> new BoardException("해당 유저가 없습니다."));
                    String manitiNickname = maniti.getNickname();
                    writer = manitiNickname + "님의 마니또";
                } else{
                    writer = "마니또";
                }
                writerEmail = null;
                writerProfileUrl = null;
            }

            Optional<Liked> liked = likedQueryRepository.findLikedByBoardAndRoomUser(board, roomUser);
            if(liked.isPresent()){
                likedYn = true;
            }
            return SearchPostResponseDto.builder()
                    .boardNo(board.getId())
                    .roomUserNo(board.getRoomUser().getId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .registerAt(board.getRegisterAt())
                    .hit(board.getHit())
                    .boardCategory(board.getBoardCategory())
                    .publicYn(board.getPublicYn())
                    .likedCount(board.getLikedCount())
                    .writer(writer)
                    .writerEmail(writerEmail)
                    .writerProfileUrl(writerProfileUrl)
                    .likedYn(likedYn)
                    .userMissionNo(board.getUserMission()!=null?board.getUserMission().getId():null)
                    .userMission(board.getUserMission()!=null?board.getUserMission().getContent():null)
                    .build();
        } catch (Exception e){
            throw new BoardException(e.getMessage());
        }
    }


    @Override
    public List<SearchReplyResponseDto> getRely(Long boardNo, Long roomNo, Long userNo) {
        try {

            RoomUser roomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, roomNo).orElseThrow(()->new BoardException("방에 참여한 유저가 아닙니다."));

            Board board = boardQueryRepository.findById(boardNo).orElseThrow(()-> new BoardException("해당 게시글이 존재하지 않습니다."));

            if(!board.getRoomUser().getRoom().equals(roomUser.getRoom())) {
                throw new BoardException("댓글을 조회할 권한이 없습니다.");
            }

            List<Reply> replyList = replyQueryRepository.findAllByBoard(board);

            List<SearchReplyResponseDto> searchReplyResponseDtoList = new ArrayList<>();

            for (Reply reply : replyList) {
                RoomUser ru = roomUserQueryRepository.findById(reply.getRoomUser().getId()).orElseThrow();
                User user = ru.getUser();

                String writer = ru.getNickname();
                String writerEmail = user.getEmail();
                String writerProfileUrl = user.getProfileUrl();

                Boolean anonymityYn = reply.getAnonymityYn();
                if (anonymityYn && reply.getRoomUser().equals(reply.getBoard().getRoomUser())) {
                    Long manitiNo = roomUser.getUsersManiti();
                    RoomUser maniti = roomUserQueryRepository.findByUserNoAndRoomNo(manitiNo, roomNo).orElseThrow(()->new BoardException("해당 유저가 없습니다."));
                    String manitiNickname = maniti.getNickname();
                    writer=manitiNickname+"님의 마니또";
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
                            .writer(writer)
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

    @Override
    public Long getMissionPostNo(Long userMissionNo, Long roomNo, Long userNo){
        try {
            RoomUser roomUser = roomUserQueryRepository.findByUserNoAndRoomNo(userNo, roomNo).orElseThrow(() -> new BoardException("방에 참여한 유저가 아닙니다."));
            UserMission userMission = userMissionQueryRepository.findById(userMissionNo).orElseThrow(() -> new BoardException("해당 미션이 없습니다."));
            Board board = boardQueryRepository.findBoardByUserMissionAndRoomUser(userMission, roomUser).orElseThrow(() -> new BoardException("해당 게시글이 없습니다."));
            return board.getId();
        } catch (Exception e){
            throw new BoardException(e.getMessage());
        }
    }
}
