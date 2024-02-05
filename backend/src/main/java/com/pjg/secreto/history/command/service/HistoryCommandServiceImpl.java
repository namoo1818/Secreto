package com.pjg.secreto.history.command.service;

import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.history.command.dto.WriteManitoWordCloudRequest;
import com.pjg.secreto.history.command.repository.WordCloudCommandRepository;
import com.pjg.secreto.history.common.entity.WordCloud;
import com.pjg.secreto.room.common.entity.QRoomUser;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.user.common.exception.UserException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class HistoryCommandServiceImpl implements HistoryCommandService {
    private final WordCloudCommandRepository wordCloudCommandRepository;
    private final JPAQueryFactory query;

    @Override
    public void writeWorldCloud(Long roomId, WriteManitoWordCloudRequest dto){
        QRoomUser roomUser = QRoomUser.roomUser;
        Long authenticatedUserId = AuthUtils.getAuthenticatedUserId();

        RoomUser user = query.selectFrom(roomUser)
                .where(roomUser.room.id.eq(roomId), roomUser.user.id.eq(authenticatedUserId))
                .fetchOne();

        if(user == null)
            throw new UserException("접근 권한이 없습니다.");

        WordCloud wordCloud = WordCloud.builder()
                .content(dto.getContents())
                .value(new Random().nextLong(100))
                .user(user)
                .build();

        wordCloudCommandRepository.save(wordCloud);
    }
}
