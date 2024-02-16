package com.pjg.secreto.history.query.repository;

import com.pjg.secreto.history.common.entity.QWordCloud;
import com.pjg.secreto.history.common.entity.WordCloud;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class WordCloudQueryRepository {
    private final JPAQueryFactory query;

    public List<List<?>> getCloudWordsData(Long roomId){
        QWordCloud wordCloud = QWordCloud.wordCloud;

        return query.select(Projections.list(wordCloud.content, wordCloud.value))
                .from(wordCloud)
                .where(wordCloud.user.room.id.eq(roomId))
                .fetch();
    }

    public Optional<WordCloud> findByContent(String content){
        QWordCloud wordCloud = QWordCloud.wordCloud;

        WordCloud result = query.selectFrom(wordCloud).where(wordCloud.content.eq(content)).fetchOne();
        return Optional.ofNullable(result);
    }
}
