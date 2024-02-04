package com.pjg.secreto.history.query.service;

import com.pjg.secreto.history.query.dto.PredictBoardDto;
import com.pjg.secreto.history.query.repository.ManitoExpectRepository;
import com.pjg.secreto.history.query.repository.WordCloudQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryQueryServiceImpl implements HistoryQueryService {
    private final WordCloudQueryRepository wordCloudQueryRepository;
    private final ManitoExpectRepository manitoExpectRepository;

    @Override
    public List<List<?>> getWorldCloudContents(Long roomId) {
        return wordCloudQueryRepository.getCloudWordsData(roomId);
    }

    @Override
    public List<PredictBoardDto> getManitoResultList(Long roomId){
        return manitoExpectRepository.getPredictResult(roomId);
    }
}
