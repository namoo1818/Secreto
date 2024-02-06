package com.pjg.secreto.history.query.service;

import com.pjg.secreto.history.query.dto.PredictBoardDto;
import com.pjg.secreto.history.query.dto.SummaryDto;

import java.util.List;

public interface HistoryQueryService {
    List<List<?>> getWorldCloudContents(Long roomId);

    public List<PredictBoardDto> getManitoResultList(Long roomId);

    List<SummaryDto> getManitoStaticResult(Long roomId);
}
