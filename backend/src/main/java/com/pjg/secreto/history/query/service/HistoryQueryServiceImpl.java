package com.pjg.secreto.history.query.service;

import com.pjg.secreto.history.query.dto.*;
import com.pjg.secreto.history.query.repository.ManitoExpectRepository;
import com.pjg.secreto.history.query.repository.StaticRepository;
import com.pjg.secreto.history.query.repository.WordCloudQueryRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryQueryServiceImpl implements HistoryQueryService {
    private final WordCloudQueryRepository wordCloudQueryRepository;
    private final ManitoExpectRepository manitoExpectRepository;
    private final StaticRepository staticRepository;

    @Override
    public List<List<?>> getWorldCloudContents(Long roomId) {
        return wordCloudQueryRepository.getCloudWordsData(roomId);
    }

    @Override
    public List<PredictBoardDto> getManitoResultList(Long roomId) {
        return manitoExpectRepository.getPredictResult(roomId);
    }

    @Override
    public List<SummaryDto> getManitoStaticResult(Long roomId){
        return List.of(
                getMostLikedPost(roomId),
                getMostViewPost(roomId),
                getBestMember(roomId),
                getFastestCorrectManito(roomId),
                getMostWroteCerticationUser(roomId),
                getMostWroteBoastUser(roomId)
        );
    }

    private SummaryDto getBestMember(Long roomId) {
        List<Tuple> bestMemberCandidate = staticRepository.getBestMember(roomId);

        List<BestMemberDto> sortedBestMemberCandidate = bestMemberCandidate.stream()
                .map(tuple -> {
                    PlayerDto playerDto = tuple.get(0, PlayerDto.class);
                    StaticsTotalCountDto staticsTotalCountDto = tuple.get(1, StaticsTotalCountDto.class);

                    return new BestMemberDto(playerDto, staticsTotalCountDto);
                })
                .sorted()
                .toList();

        BestMemberDto bestMember = sortedBestMemberCandidate.get(0);

        SummaryDto summaryDto = new SummaryDto(
                "최고의 맴버는?",
                new SummaryResultData(
                        bestMember.getPlayer().getNickname(),
                        bestMember.contents(),
                        bestMember.getPlayer().getProfileUrl()
                )
        );
        return summaryDto;
    }

    private SummaryDto getMostViewPost(Long roomId){
        SummaryResultData mostViewPost = staticRepository.getMostViewPost(roomId);
        return new SummaryDto(
                "조회수가 가장 높은 게시글을 작성한 사람",
                mostViewPost
        );
    }

    private SummaryDto getMostLikedPost(Long roomId){
        SummaryResultData mostLikedPost = staticRepository.getMostLikedPost(roomId);

        return new SummaryDto(
                "가장 많이 좋아요를 받은 사람",
                mostLikedPost
        );
    }

    private SummaryDto getMostWroteCerticationUser(Long roomId){
        SummaryResultData mostWroteCerticationUser = staticRepository.getMostWroteCerticationUser(roomId);
        return new SummaryDto(
                "가장 많이 인증글을 작성한 사람",
                mostWroteCerticationUser
        );
    }

    private SummaryDto getMostWroteBoastUser(Long roomId){
        SummaryResultData mostWroteBoastUser = staticRepository.getMostWroteBoastUser(roomId);
        return new SummaryDto(
                "가장 많은 자랑글을 작성한 사람",
                mostWroteBoastUser
        );
    }

    private SummaryDto getFastestCorrectManito(Long roomId){
        SummaryResultData fastestCorrectManito = manitoExpectRepository.getFastestCorrectManito(roomId);

        return new SummaryDto(
                "가장 빠르게 마니또를 맞춘 사람",
                fastestCorrectManito
        );
    }
}
