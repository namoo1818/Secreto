package com.pjg.secreto.history.query.repository;

import com.pjg.secreto.history.common.entity.ManitoExpectLog;
import com.pjg.secreto.history.common.entity.QManitoExpectLog;
import com.pjg.secreto.history.common.entity.QMatching;
import com.pjg.secreto.history.query.dto.*;
import com.pjg.secreto.room.common.entity.QRoom;
import com.pjg.secreto.room.common.entity.QRoomUser;
import com.pjg.secreto.room.common.entity.Room;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.user.common.entity.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ManitoExpectRepository {
    private final JPAQueryFactory query;

    public List<PredictBoardDto> getMatchingResult(Long roomId) {
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

        return result;
    }

    public SummaryResultData getFastestCorrectManito(Long roomId) {
        QManitoExpectLog manitoExpectLog = QManitoExpectLog.manitoExpectLog;
        QMatching matching = QMatching.matching;

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
                .fetchFirst();

        return result;
    }


    public List<PredictorDto> getPredictResult(Long roomId, List<RoomUser> users) {
        QManitoExpectLog expectLog = QManitoExpectLog.manitoExpectLog;
        QMatching matching = QMatching.matching;
        QRoomUser targetRoomUser = new QRoomUser("targetRoomUser");
        QMatching subMatching = new QMatching("subMatching");
        LocalDateTime today = LocalDateTime.now();

        List<PredictorDto> result = query
                .select(
                        new QPredictorDto(
                                expectLog.id,
                                expectLog.expectedAt,
                                expectLog.roomUser.nickname,
                                targetRoomUser.nickname,
                                expectLog.expectedReason
                        )
                )
                .from(expectLog)
                .leftJoin(matching).on(expectLog.roomUser.eq(matching.roomUser))
                .leftJoin(targetRoomUser).on(targetRoomUser.id.eq(expectLog.expectedUser)).fetchJoin()
                .where(expectLog.roomUser.room.id.eq(roomId))
                .where(expectLog.roomUser.in(users))
                .where(expectLog.expectedAt.before(matching.roomUser.room.roomEndAt))
                .fetch();

        return result;
    }

}
