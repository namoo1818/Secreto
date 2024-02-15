package com.pjg.secreto.room.query.repository;

import com.pjg.secreto.history.common.entity.Matching;
import com.pjg.secreto.history.common.entity.QMatching;
import com.pjg.secreto.room.common.entity.QRoomUser;
import com.pjg.secreto.room.query.dto.SearchRoomUserListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.expression.spel.ast.Projection;

import java.util.List;

import static com.pjg.secreto.history.common.entity.QMatching.*;
import static com.pjg.secreto.room.common.entity.QRoomUser.*;
import static com.pjg.secreto.room.common.entity.QRoomUser.roomUser;

public class RoomUserQueryRepositoryImpl implements RoomUserQueryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public RoomUserQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


}
