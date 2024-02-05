package com.pjg.secreto.history.query.repository;

import com.pjg.secreto.history.common.entity.QManitoExpectLog;
import com.pjg.secreto.history.common.entity.QMatching;
import com.pjg.secreto.history.query.dto.*;
import com.pjg.secreto.room.common.entity.QRoomUser;
import com.pjg.secreto.user.common.entity.QUser;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ManitoExpectRepository {
    private final JPAQueryFactory query;

    public List<PredictBoardDto> getPredictResult(Long roomId) {
        QManitoExpectLog manitoExpectLog = QManitoExpectLog.manitoExpectLog;
        QMatching matching = QMatching.matching;
        QMatching subMatching = new QMatching("subMatching");
        QManitoExpectLog subManitoExpectedLog = new QManitoExpectLog("subManitoExpectedLog");

        QRoomUser maniti = matching.roomUser;
        QRoomUser manito = new QRoomUser("manito");
        QUser manitiUser = new QUser("manitiUser");
        QUser manitoUser = new QUser("manitoUser");


        JPQLQuery<Long> expectedByManito = JPAExpressions.select(subManitoExpectedLog.expectedUser)
                .from(subManitoExpectedLog)
                .where(subManitoExpectedLog.roomUser.id.eq(matching.manitoNo))
                .groupBy(subManitoExpectedLog.roomUser)
                .orderBy(subManitoExpectedLog.expectedAt.desc());

        List<PredictBoardDto> result = query
                .select(
                        new QPredictBoardDto(
                                new QPlayerDto(maniti),
                                new QPlayerDto(manito),
                                new CaseBuilder().when(expectedByManito.eq(matching.roomUser.id))
                                        .then(true)
                                        .otherwise(false)
                        )
                )
                .from(matching)
                .leftJoin(manitoExpectLog).on(matching.roomUser.eq(manitoExpectLog.roomUser))
                .join(maniti.user, manitiUser).fetchJoin()
                .join(manito).on(matching.manitoNo.eq(manito.id))
                .join(manito.user, manitoUser).fetchJoin()
                .where(manitoExpectLog.roomUser.room.id.eq(roomId),
                        manitoExpectLog.expectedAt.in(
                                JPAExpressions.select(manitoExpectLog.expectedAt.max())
                                        .from(manitoExpectLog)
                                        .groupBy(manitoExpectLog.roomUser)
                                        .orderBy(manitoExpectLog.expectedAt.desc())
                        ),
                        matching.deprecatedAt.isNull()
                )
                .fetch();

        return result;
    }

    public SummaryResultData getFastestCorrectManito(Long roomId){
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
                .leftJoin(manitoExpectLog).on(matching.roomUser.eq(manitoExpectLog.roomUser))
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
                .orderBy(manitoExpectLog.expectedAt.asc())
                .fetchFirst();


        return result;
    }
}
