package com.pjg.secreto.history.query.service;

import com.pjg.secreto.board.common.entity.BoardCategory;
import com.pjg.secreto.history.query.dto.*;
import com.pjg.secreto.history.query.repository.ManitoActivityRepository;
import com.pjg.secreto.history.query.repository.ManitoExpectRepository;
import com.pjg.secreto.history.query.repository.StaticRepository;
import com.pjg.secreto.history.query.repository.WordCloudQueryRepository;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.room.query.repository.RoomUserQueryRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryQueryServiceImpl implements HistoryQueryService {
    private final WordCloudQueryRepository wordCloudQueryRepository;
    private final ManitoExpectRepository manitoExpectRepository;
    private final StaticRepository staticRepository;
    private final RoomUserQueryRepository roomUserQueryRepository;
    private final ManitoActivityRepository manitoActivityRepository;

    @Override
    public List<List<?>> getWorldCloudContents(Long roomId) {
        return wordCloudQueryRepository.getCloudWordsData(roomId);
    }

    @Override
    public List<PredictBoardDto> getManitoResultList(Long roomId) {
        return manitoExpectRepository.getMatchingResult(roomId);
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

    @Override
    public Map<String, Object> getMyManitoActivity(Long roomId, Long roomUserId) {
        Long authenticatedUserId = roomUserId;
//        RoomUser principal = roomUserQueryRepository.findById(authenticatedUserId).orElseThrow();
        RoomUser principal = roomUserQueryRepository.findByUserNoAndRoomNo(authenticatedUserId, roomId).orElseThrow();
        List<RoomUser> targets = roomUserQueryRepository.findAllByUsersManiti(roomId, principal.getId());

        log.info("마니또 : " + principal.getNickname() + " " + principal.getId());
        targets.forEach(s -> log.info("놈 : " + s.getNickname() + " " + s.getId() + "\n"));

        List<PostDto> myCerticiationActivity = manitoActivityRepository
                .getBoardActivity(roomId, targets, BoardCategory.CERTIFICATE);
        List<PostDto> targetsBoastActivity = manitoActivityRepository
                .getBoardActivity(roomId, List.of(principal), BoardCategory.BOAST);

        List<PredictorDto> targetsPredictor = manitoExpectRepository.getPredictResult(roomId, List.of(principal));
        targetsPredictor.forEach(s -> System.out.println("마니또가 추리한 사람들 : "+ s.getId() + " " + s.getTargetNickName() + " \n"));

        List<? super Object> manitos = new ArrayList<>();
        manitos.addAll(targetsBoastActivity);
        manitos.addAll(targetsPredictor);


        List<? super Object> manitis = new ArrayList<>();
        manitis.addAll(myCerticiationActivity);

        TreeMap<Object, List<Object>> manito = getTreeMap(manitos);
        TreeMap<Object, List<Object>> maniti = getTreeMap(manitis);

        Map<String, Object> result = new HashMap<>();
        result.put("maniti", getClassification(maniti));
        result.put("manito", getClassification(manito));

        return result;
    }

    @Override
    public Map<String, Object> getMyManitiActivity(Long roomId, Long roomUserId) {
        Long authenticatedUserId = roomUserId;
        RoomUser principal = roomUserQueryRepository.findByUserNoAndRoomNo(authenticatedUserId, roomId).orElseThrow();
        List<RoomUser> targets = roomUserQueryRepository.findAllByUsersManito(roomId, principal.getId());
        log.info("내가 마니띠 일때");
        log.info("사람: " + principal.getNickname() + " " + principal.getId());
        targets.forEach(s -> log.info("놈 : " + s.getNickname() + " " + s.getId() + "\n"));


        List<PostDto> myCerticiationActivity = manitoActivityRepository
                .getBoardActivity(roomId, List.of(principal), BoardCategory.CERTIFICATE);
        List<PostDto> targetsBoastActivity = manitoActivityRepository
                .getBoardActivity(roomId, targets, BoardCategory.BOAST);

        List<PredictorDto> targetsPredictor = manitoExpectRepository.getPredictResult(roomId, targets);

        List<? super Object> manitos = new ArrayList<>();
        manitos.addAll(targetsBoastActivity);
        manitos.addAll(targetsPredictor);

        List<? super Object> manitis = new ArrayList<>();
        manitis.addAll(myCerticiationActivity);

        TreeMap<Object, List<Object>> manito = getTreeMap(manitos);
        TreeMap<Object, List<Object>> maniti = getTreeMap(manitis);

        Map<String, Object> result = new HashMap<>();
        result.put("maniti", getClassification(maniti));
        result.put("manito", getClassification(manito));

        return result;
    }

    private TreeMap<Object, List<Object>> getTreeMap(List<?> collection){
        TreeMap<Object, List<Object>> result  = collection
                .stream()
                .collect(TreeMap::new,
                        (map, obj) -> {
                            LocalDate date;
                            if (obj instanceof PostDto) {
                                date = ((PostDto) obj).getEntryAt().toLocalDate();
                            } else if (obj instanceof PredictorDto) {
                                date = ((PredictorDto) obj).getEntryAt().toLocalDate();
                            } else {
                                throw new IllegalArgumentException("Unsupported type: " + obj.getClass());
                            }

                            map.computeIfAbsent(date, k -> new ArrayList<>() {}).add(obj);
                        },  TreeMap::putAll);

        return result;
    }

    private Map<Object, Map<String, List<Object>>> getClassification(TreeMap<Object, List<Object>> collections){
        Map<Object, Map<String, List<Object>>> result = new LinkedHashMap<>();

        for (Map.Entry<Object, List<Object>> entry : collections.entrySet()) {

            Object key = entry.getKey();
            List<Object> value = entry.getValue();

            Map<String, List<Object>> collect = value.stream()
                    .collect(Collectors.groupingBy(
                            d -> d.getClass().getSimpleName().substring(0, d.getClass().getSimpleName().length() - 3).toLowerCase(),
                            LinkedHashMap::new,
                            Collectors.toList()
                    ));

            result.put(key, collect);
        }

        return result;
    }


    private SummaryDto getBestMember(Long roomId) {
        List<Tuple> bestMemberCandidate = staticRepository.getBestMember(roomId);

        List<BestMemberDto> sortedBestMemberCandidate = bestMemberCandidate.stream()
                .map(tuple -> {
                    PlayerDto playerDto = tuple.get(0, PlayerDto.class);
                    StaticsTotalCountDto staticsTotalCountDto = tuple.get(1, StaticsTotalCountDto.class);

                    return new BestMemberDto(playerDto, staticsTotalCountDto);
                })
                .sorted(BestMemberDto::compareTo)
                .toList();

        if(sortedBestMemberCandidate.isEmpty()){
            return new SummaryDto(
                    "최고의 맴버는?",
                    new SummaryResultData(
                            null,
                            "선정된 사람이 없습니다.",
                            null
                    )
            );
        }

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
