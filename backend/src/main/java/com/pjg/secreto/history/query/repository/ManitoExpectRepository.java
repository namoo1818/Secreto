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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.pjg.secreto.room.common.entity.QRoomUser.roomUser;
import static com.pjg.secreto.user.common.entity.QUser.user;


@Repository
@RequiredArgsConstructor
@Slf4j
public class ManitoExpectRepository {
    private final JPAQueryFactory query;
    private final ManitoExpectJdbcRepository manitoExpectJdbcRepository;


    public List<PredictBoardDto> getMatchingResult(Long roomId) {
        List<ManitoExpectedBoard> manitoExpectedBoards = manitoExpectJdbcRepository.find(roomId);
        Map<Long, Integer> indexer = new HashMap<>();

        int userCount = manitoExpectedBoards.size();
        for (int i = 0; i < userCount; ++i) {
            ManitoExpectedBoard b = manitoExpectedBoards.get(i);
            indexer.put(b.getRoomUserNo(), i);
        }

        List<Long> roomUsers = manitoExpectedBoards.stream()
                .map(r -> r.getManitoRoomUserNo())
                .collect(Collectors.toList());

        QRoomUser roomUser = new QRoomUser("roomUser");
        QUser user = new QUser("user");

        List<RoomUser> players = query.select(roomUser)
                .from(roomUser)
                .join(roomUser.user, user).fetchJoin()
                .where(roomUser.id.in(roomUsers))
                .fetch();

        Map<Long, RoomUser> playerMap = players.stream()
                .collect(Collectors.toMap(RoomUser::getId, Function.identity()));

        List<PredictBoardDto> result = new ArrayList<>();

        List<ManitoExpectedBoard> reassembled = new ArrayList<>(userCount);
        Long curr = manitoExpectedBoards.get(0).getRoomUserNo();
        for (int i = 0; i < userCount; ++i) {
            reassembled.add(
                    manitoExpectedBoards.get(indexer.get(curr))
            );
            curr = manitoExpectedBoards.get(indexer.get(curr)).getManitoRoomUserNo();
        }

        for (ManitoExpectedBoard manito: reassembled){
            PlayerDto giver = new PlayerDto(playerMap.get(manito.getRoomUserNo()));
            PlayerDto given = new PlayerDto(playerMap.get(manito.getManitoRoomUserNo()));
            boolean predictCorrect = manito.isPredictCorrect();

            PredictBoardDto predictBoardDto = new PredictBoardDto(giver, given, predictCorrect);
            result.add(predictBoardDto);
        }

        return result;
    }

    public SummaryResultData getFastestCorrectManito(Long roomId) {
        List<ManitoExpectedBoard> manitoExpectedBoards = manitoExpectJdbcRepository.find(roomId);
        Optional<ManitoExpectedBoard> first = manitoExpectedBoards.stream()
                .filter(ManitoExpectedBoard::isPredictCorrect)
                .sorted(Comparator.comparing(ManitoExpectedBoard::getPredictAt))
                .findFirst();

        if(first.isPresent()){
            ManitoExpectedBoard manitoExpectedBoard = first.orElseThrow();
            PlayerDto playerDto = query.select(new QPlayerDto(roomUser))
                    .from(roomUser)
                    .join(roomUser.user, user).fetchJoin()
                    .where(roomUser.id.eq(manitoExpectedBoard.getRoomUserNo()))
                    .fetchOne();

            return new SummaryResultData(
                    playerDto.getNickname(),
                    manitoExpectedBoard.getPredictAt(),
                    playerDto.getProfileUrl()
            );
        }

        return new SummaryResultData("아무도 없습니다.",LocalDateTime.of(0,0,0,0,0,0) , "");
    }


    public List<PredictorDto> getPredictResult(Long roomId, List<RoomUser> users) {
        log.info("추론하기");
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
