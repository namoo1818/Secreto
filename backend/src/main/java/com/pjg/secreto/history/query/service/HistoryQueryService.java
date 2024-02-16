package com.pjg.secreto.history.query.service;

import com.pjg.secreto.history.query.dto.PredictBoardDto;
import com.pjg.secreto.history.query.dto.SummaryDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface HistoryQueryService {
    List<List<?>> getWorldCloudContents(Long roomId);

    public List<PredictBoardDto> getManitoResultList(Long roomId);

    List<SummaryDto> getManitoStaticResult(Long roomId);

    Map<String, Object> getMyManitiActivity(Long roomId, Long roomUserId);

    Map<String, Object> getMyManitoActivity(Long roomId, Long roomUserId);
}
