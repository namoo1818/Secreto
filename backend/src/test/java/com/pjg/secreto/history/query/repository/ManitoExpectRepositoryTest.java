package com.pjg.secreto.history.query.repository;

import com.pjg.secreto.history.common.entity.QManitoExpectLog;
import com.pjg.secreto.history.common.entity.QMatching;
import com.pjg.secreto.history.query.dto.PredictBoardDto;
import com.pjg.secreto.history.query.dto.QPlayerDto;
import com.pjg.secreto.history.query.dto.QPredictBoardDto;
import com.pjg.secreto.room.common.entity.QRoomUser;
import com.pjg.secreto.user.common.entity.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ManitoExpectRepositoryTest {
    @Autowired
    ManitoExpectRepository manitoExpectRepository;

    @Autowired
    JPAQueryFactory query;

    @Test
    void getPredictResult() {
        List<PredictBoardDto> predictResult = manitoExpectRepository.getPredictResult(1L);
        for (PredictBoardDto predict : predictResult) {
            System.out.println(predict.toString());
        }

    }
//
//    @Test
//    void getRoomUser3() {
//        long roomId = 1L;
//
//        QManitoExpectLog manitoExpectLog = QManitoExpectLog.manitoExpectLog;
//        QRoomUser roomUser1 = new QRoomUser("r1");
//        QRoomUser roomUser2 = new QRoomUser("r2");
//        QRoomUser sub1 = new QRoomUser("sub1");
//        QRoomUser sub2 = new QRoomUser("sub2");
//
//
//        QUser subUser1 = new QUser("subUser1");
//        QUser subUser2 = new QUser("subUser2");
//
//
//        List<Tuple> fetch = query
//                .select(
//                        new QPlayerDto(manitoExpectLog.roomUser),
//                        new QPlayerDto(roomUser1)
//                )
//                .from(manitoExpectLog)
//                .join(roomUser1).on(roomUser1.id.eq(manitoExpectLog.expectedUser))
//                .join(manitoExpectLog.roomUser.user, subUser2).fetchJoin()
//                .join(roomUser1.user, subUser1).fetchJoin()
//                .where(manitoExpectLog.roomUser.room.id.eq(roomId))
//                .fetch();
//
//        for(Object o : fetch){
//            System.out.println(o);
//        }
//    }
//
//    @Test
//    void getRoomUser4() {
//        long roomId = 2L;
//
//        QManitoExpectLog manitoExpectLog = QManitoExpectLog.manitoExpectLog;
//        QMatching matching = QMatching.matching;
//        QMatching subMatching = new QMatching("subMatching");
//        QManitoExpectLog subManitoExpectedLog = new QManitoExpectLog("subManitoExpectedLog");
//
//        QRoomUser maniti = matching.roomUser;
//        QRoomUser manito = new QRoomUser("manito");
//        QUser manitiUser = new QUser("manitiUser");
//        QUser manitoUser = new QUser("manitoUser");
//
//
//        JPQLQuery<Long> expectedByManito = JPAExpressions.select(subManitoExpectedLog.expectedUser)
//                .from(subManitoExpectedLog)
//                .where(subManitoExpectedLog.roomUser.id.eq(matching.manitoNo))
//                .groupBy(subManitoExpectedLog.roomUser)
//                .orderBy(subManitoExpectedLog.expectedAt.desc());
//
//        List<Tuple> fetch = query
//                .select(
//                        new QPlayerDto(maniti),
//                        new QPlayerDto(manito),
//                        new CaseBuilder().when(expectedByManito.eq(matching.roomUser.id))
//                                .then(true)
//                                .otherwise(false)
//                )
//                .from(matching)
//                .leftJoin(manitoExpectLog).on(matching.roomUser.eq(manitoExpectLog.roomUser))
//                .join(maniti.user, manitiUser).fetchJoin()
//                .join(manito).on(matching.manitoNo.eq(manito.id))
//                .join(manito.user, manitoUser).fetchJoin()
//                .where(manitoExpectLog.roomUser.room.id.eq(roomId),
//                        manitoExpectLog.expectedAt.in(
//                                JPAExpressions.select(manitoExpectLog.expectedAt.max())
//                                        .from(manitoExpectLog)
//                                        .groupBy(manitoExpectLog.roomUser)
//                                        .orderBy(manitoExpectLog.expectedAt.desc())
//                        ),
//                        matching.deprecatedAt.isNull()
//                )
//                .fetch();
//
//
//        for(Object o : fetch){
//            System.out.println(o);
//        }
//    }

    @Test
    void testStatics(){

    }

}