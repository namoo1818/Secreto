package com.pjg.secreto.history.query.repository;

import com.pjg.secreto.board.common.entity.BoardCategory;
import com.pjg.secreto.board.common.entity.QBoard;
import com.pjg.secreto.history.query.dto.QSummaryResultData;
import com.pjg.secreto.history.query.dto.SummaryResultData;
import com.pjg.secreto.room.common.entity.QRoomUser;
import com.pjg.secreto.user.common.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StaticRepository {
    private final JPAQueryFactory query;

    public SummaryResultData getMostLikedPost(Long roomId){
        QBoard board = QBoard.board;
        QRoomUser roomUser = QRoomUser.roomUser;
        QUser user = QUser.user;

        SummaryResultData result = query.select(new QSummaryResultData(
                        board.title,
                        board.roomUser.nickname,
                        board.likedCount,
                        board.content,
                        board.registerAt,
                        board.roomUser.user.profileUrl
                ))
                .from(board)
                .join(board.roomUser, roomUser)
                .join(board.roomUser.user, user)
                .orderBy(board.likedCount.desc(), board.registerAt.asc())
                .where(board.roomUser.room.id.eq(roomId))
                .fetchFirst();

        return result;
    }

    public SummaryResultData getMostViewPost(Long roomId){
        QBoard board = QBoard.board;
        QRoomUser roomUser = QRoomUser.roomUser;
        QUser user = QUser.user;

        SummaryResultData result = query.select(new QSummaryResultData(
                        board.title,
                        board.roomUser.nickname,
                        board.hit,
                        board.content,
                        board.registerAt,
                        board.roomUser.user.profileUrl
                ))
                .from(board)
                .join(board.roomUser, roomUser)
                .join(board.roomUser.user, user)
                .orderBy(board.likedCount.desc(), board.registerAt.asc())
                .where(board.roomUser.room.id.eq(roomId))
                .fetchFirst();

        return result;
    }

    public SummaryResultData getMostWroteCerticationUser(Long roomId){
        QBoard board = QBoard.board;
        QRoomUser roomUser = QRoomUser.roomUser;
        QUser user = QUser.user;

        SummaryResultData result = query.select(new QSummaryResultData(
                        board.roomUser.nickname,
                        board.count(),
                        board.roomUser.user.profileUrl
                ))
                .from(board)
                .join(board.roomUser, roomUser)
                .join(board.roomUser.user, user)
                .where(board.roomUser.room.id.eq(roomId))
                .where(board.boardCategory.eq(BoardCategory.CERTIFICATE))
                .groupBy(board.roomUser)
                .orderBy(board.count().desc())
                .fetchFirst();

        return result;
    }

    public SummaryResultData getMostWroteBoastUser(Long roomId){
        QBoard board = QBoard.board;
        QRoomUser roomUser = QRoomUser.roomUser;
        QUser user = QUser.user;

        SummaryResultData result = query.select(new QSummaryResultData(
                        board.roomUser.nickname,
                        board.count(),
                        board.roomUser.user.profileUrl
                ))
                .from(board)
                .join(board.roomUser, roomUser)
                .join(board.roomUser.user, user)
                .where(board.roomUser.room.id.eq(roomId))
                .where(board.boardCategory.eq(BoardCategory.BOAST))
                .groupBy(board.roomUser)
                .orderBy(board.count().desc())
                .fetchFirst();

        return result;
    }


}
