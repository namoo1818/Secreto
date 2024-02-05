package com.pjg.secreto.history.command.service;

import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.history.command.dto.WriteManitoWordCloudRequest;
import com.pjg.secreto.history.command.repository.WordCloudCommandRepository;
import com.pjg.secreto.history.common.entity.WordCloud;
import com.pjg.secreto.room.common.entity.QRoomUser;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.user.common.entity.QUser;
import com.pjg.secreto.user.common.exception.UserException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class WordCloudCommandServiceImpl implements WordCloudCommandService {
    private final JPAQueryFactory query;
    private final WordCloudCommandRepository wordCloudCommandRepository;

    @Override
    public void writeWordCloudCommand(Long roomId, WriteManitoWordCloudRequest dto) {
        Long authenticatedUserId = AuthUtils.getAuthenticatedUserId();
        QRoomUser roomUser = QRoomUser.roomUser;

        RoomUser user = query.selectFrom(roomUser)
                .where(roomUser.room.id.eq(roomId), roomUser.user.id.eq(authenticatedUserId))
                .fetchOne();

        if(user == null){
            throw new UserException("접근권한이 없습니다.");
        }

        wordCloudCommandRepository.save(new WordCloud(user, dto.getContents(), new Random().nextLong(100)));
    }
}
