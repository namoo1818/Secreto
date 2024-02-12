package com.pjg.secreto.history.query.repository;


import com.pjg.secreto.board.common.entity.BoardCategory;
import com.pjg.secreto.board.common.entity.QBoard;
import com.pjg.secreto.history.common.entity.QMatching;
import com.pjg.secreto.history.query.dto.PostDto;
import com.pjg.secreto.history.query.dto.QPostDto;
import com.pjg.secreto.mission.common.entity.QUserMission;
import com.pjg.secreto.room.common.entity.QRoom;
import com.pjg.secreto.room.common.entity.QRoomUser;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.user.common.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ManitoActivityRepository {
    private final JPAQueryFactory query;

    public List<PostDto> getBoardActivity(Long roomId, List<RoomUser> users, BoardCategory category){
        QMatching matching = QMatching.matching;
        QBoard board = QBoard.board;
        QRoomUser roomUser = QRoomUser.roomUser;
        QUser user = QUser.user;
        QRoom room = QRoom.room;
        QUserMission userMission = QUserMission.userMission;

        List<PostDto> result = query.select(new QPostDto(board))
                .from(board)
                .leftJoin(board.roomUser, roomUser).fetchJoin()
                .leftJoin(board.roomUser.user, user).fetchJoin()
                .leftJoin(userMission).on(userMission.eq(board.userMission))
                .where(board.roomUser.room.id.eq(roomId))
                .where(board.roomUser.in(users))
                .where(board.boardCategory.eq(category))
                .where(board.registerAt.before(board.roomUser.room.roomEndAt))
                .fetch();

        return result;
    }
}
