package com.pjg.secreto.history.command.service;

import ch.qos.logback.core.testUtil.RandomUtil;
import com.pjg.secreto.alarm.dto.AlarmDataDto;
import com.pjg.secreto.alarm.service.EmitterService;
import com.pjg.secreto.common.Util.AuthUtils;
import com.pjg.secreto.history.command.dto.WriteManitoWordCloudRequest;
import com.pjg.secreto.history.command.repository.WordCloudCommandRepository;
import com.pjg.secreto.history.common.entity.WordCloud;
import com.pjg.secreto.history.query.repository.WordCloudQueryRepository;
import com.pjg.secreto.room.common.entity.QRoomUser;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import com.pjg.secreto.user.common.entity.QUser;
import com.pjg.secreto.user.common.exception.UserException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WordCloudCommandServiceImpl implements WordCloudCommandService {
    private final JPAQueryFactory query;
    private final WordCloudCommandRepository wordCloudCommandRepository;
    private final WordCloudQueryRepository wordCloudQueryRepository;
    private final EmitterService emitterService;
    private final RoomUserQueryRepository roomUserQueryRepository;

    @Override
    public void writeWordCloudCommand(Long roomId, WriteManitoWordCloudRequest dto) {
        Long authenticatedUserId = AuthUtils.getAuthenticatedUserId();
        QRoomUser roomUser = QRoomUser.roomUser;

        RoomUser user = query.selectFrom(roomUser)
                .where(roomUser.room.id.eq(roomId), roomUser.user.id.eq(authenticatedUserId))
                .fetchOne();

        if (user == null) {
            throw new UserException("접근권한이 없습니다.");
        }

        Optional<WordCloud> byContent = wordCloudQueryRepository.findByContent(dto.getContents());

        WordCloud entity = byContent.orElseGet(() -> new WordCloud(user, dto.getContents(), 0L));
        entity.setValue(entity.getValue()+1);
        wordCloudCommandRepository.save(entity);

        List<RoomUser> findRoomUsers = roomUserQueryRepository.findAllByRoomNo(roomId);

        for(RoomUser ru : findRoomUsers) {

            // 유저에게 알림 발송
            AlarmDataDto alarmDataDto = AlarmDataDto.builder()
                    .content(dto.getContents())
                    .readYn(false)
                    .generatedAt(LocalDateTime.now())
                    .author("방 유저")
                    .roomUserNo(ru.getId()).build();

            emitterService.alarmWithNoStore(ru.getId(), alarmDataDto, "워드 클라우드가 작성되었습니다.", "cloud");
        }

    }
}
