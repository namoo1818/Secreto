package com.pjg.secreto.history.query.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjg.secreto.history.common.entity.QManitoExpectLog;
import com.pjg.secreto.history.common.entity.QMatching;
import com.pjg.secreto.history.query.dto.*;
import com.pjg.secreto.mission.common.entity.QMissionSchedule;
import com.pjg.secreto.mission.common.entity.QRoomMission;
import com.pjg.secreto.room.common.entity.QRoom;
import com.pjg.secreto.room.common.entity.QRoomUser;
import com.pjg.secreto.user.common.entity.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HistoryQueryServiceImplTest {
    @Autowired
    HistoryQueryService historyQueryService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JPAQueryFactory query;

    @Test
    void test1(){
        List<SummaryDto> manitoStaticResult = historyQueryService.getManitoStaticResult(1L);

        manitoStaticResult.stream()
                .forEach(System.out::println);
    }
    
    @Test
    @WithMockUser(username = "user3@example.com", password = "1234")
    void test2() throws JsonProcessingException {
        Map<String, Object> myManitiActivity = historyQueryService.getMyManitiActivity(1L,3L);

        String s = objectMapper.writeValueAsString(myManitiActivity);
        System.out.println(s);
    }



    @Test
    void test4(){
        long roomId = 1L;

        QManitoExpectLog manitoExpectLog = QManitoExpectLog.manitoExpectLog;
        QMatching matching = QMatching.matching;
        QMatching subMatching = new QMatching("subMatching");
        QManitoExpectLog subManitoExpectedLog = new QManitoExpectLog("subManitoExpectedLog");

        QRoomUser maniti = matching.roomUser;
        QRoomUser manito = new QRoomUser("manito");
        QUser manitiUser = new QUser("manitiUser");
        QUser manitoUser = new QUser("manitoUser");

        JPQLQuery<Long> expectedbymanito = JPAExpressions.select(subManitoExpectedLog.expectedUser)
                .from(subManitoExpectedLog)
                .where(subManitoExpectedLog.roomUser.id.eq(matching.manitoNo))
                .where(subManitoExpectedLog.id.eq(JPAExpressions.select(subManitoExpectedLog.id.max())
                        .from(subManitoExpectedLog)
                        .where(subManitoExpectedLog.roomUser.id.eq(matching.manitoNo)))
                )
                .where(subManitoExpectedLog.expectedAt.before(matching.roomUser.room.roomEndAt))
                .groupBy(subManitoExpectedLog.roomUser)
                .orderBy(subManitoExpectedLog.expectedAt.desc());

        List<PredictBoardDto> result = query
                .select(
                        new QPredictBoardDto(
                                new QPlayerDto(maniti),
                                new QPlayerDto(manito),
                                new CaseBuilder().when(expectedbymanito.eq(matching.roomUser.id)).then(true).otherwise(false)
                        )
                )
                .distinct()
                .from(matching)
                .leftJoin(manitoExpectLog).on(matching.roomUser.eq(manitoExpectLog.roomUser))
                .join(maniti.user, manitiUser).fetchJoin()
                .join(manito).on(matching.manitoNo.eq(manito.id))
                .join(manito.user, manitoUser).fetchJoin()
                .where(manitoExpectLog.roomUser.room.id.eq(roomId),
                        manitoExpectLog.expectedAt.in(
                                JPAExpressions.select(manitoExpectLog.expectedAt.max())
                                        .from(manitoExpectLog)
                                        .where(manitoExpectLog.roomUser.room.id.eq(roomId))
                                        .groupBy(manitoExpectLog.roomUser)
                                        .orderBy(manitoExpectLog.expectedAt.desc())
                        ),
                        matching.deprecatedAt.isNull()
                )
                .where(manitoExpectLog.expectedAt.before(matching.roomUser.room.roomEndAt))
                .fetch();

        result.stream().forEach(System.out::println);

    }

    @Test
    void test5(){
        Long roomId = 1L;

        QManitoExpectLog manitoExpectLog = QManitoExpectLog.manitoExpectLog;
        QMatching matching = QMatching.matching;
        QMatching subMatching = new QMatching("subMatching");
        QManitoExpectLog subManitoExpectedLog = new QManitoExpectLog("subManitoExpectedLog");

        QRoomUser manito = new QRoomUser("manito");
        QUser manitoUser = new QUser("manitoUser");

        SummaryResultData result = query.select(
                        new QSummaryResultData(
                                matching.roomUser.nickname,
                                manitoExpectLog.expectedAt,
                                matching.roomUser.user.profileUrl
                        ))
                .from(matching)
                .join(manitoExpectLog).on(matching.roomUser.eq(manitoExpectLog.roomUser))
                .join(manito).on(matching.manitoNo.eq(manito.id))
                .join(manito.user, manitoUser)
                .where(manitoExpectLog.roomUser.room.id.eq(roomId),
                        manitoExpectLog.expectedAt.in(
                                JPAExpressions.select(manitoExpectLog.expectedAt.max())
                                        .from(manitoExpectLog)
                                        .groupBy(manitoExpectLog.roomUser)
                                        .orderBy(manitoExpectLog.expectedAt.desc())
                        ),
                        matching.deprecatedAt.isNull(),
                        matching.manitiNo.eq(manitoExpectLog.expectedUser)
                )
                .where(manitoExpectLog.expectedAt.before(matching.roomUser.room.roomEndAt))
                .orderBy(manitoExpectLog.expectedAt.asc())
                .fetchOne();

    }

    @Test
    void test6() throws JsonProcessingException {
        Map<String, Object> myManitoActivity = historyQueryService.getMyManitiActivity(1L, 3L);
        String s = objectMapper.writeValueAsString(myManitoActivity);
        System.out.println(s);
    }

    @Test
    void test3() throws JsonProcessingException {
        Map<String, Object> myManitoActivity = historyQueryService.getMyManitoActivity(1L, 3L);
        String s = objectMapper.writeValueAsString(myManitoActivity);
        System.out.println(s);
    }




}