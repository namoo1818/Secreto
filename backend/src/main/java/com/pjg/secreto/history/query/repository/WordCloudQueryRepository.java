package com.pjg.secreto.history.query.repository;

import com.pjg.secreto.history.common.entity.QWordCloud;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WordCloudQueryRepository {
    private final JPAQueryFactory query;

    public List<List<?>>getCloudWordsData(Long roomId){
        QWordCloud wordCloud = QWordCloud.wordCloud;

        return query.select(Projections.list(wordCloud.content, wordCloud.weight))
                .from(wordCloud)
                .where(wordCloud.user.room.id.eq(roomId))
                .fetch();
    }
}
