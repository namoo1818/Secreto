package com.pjg.secreto.room.query.repository;

import com.pjg.secreto.room.common.entity.QRoom;
import com.pjg.secreto.room.common.entity.Room;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.pjg.secreto.room.common.entity.QRoom.*;

@Repository
public class RoomQueryRepositoryImpl implements RoomQueryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public RoomQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

}
