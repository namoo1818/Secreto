package com.pjg.secreto.history.query.controller;

import com.pjg.secreto.common.response.SuccessResponse;
import com.pjg.secreto.history.query.dto.*;
import com.pjg.secreto.history.query.service.HistoryQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("history")
@RequiredArgsConstructor
public class HistoryQueryController {
    private final HistoryQueryService historyQueryService;

    @GetMapping("/{roomId}/predict")
    public ResponseEntity<?> predictManittoResult(@PathVariable Long roomId) {
        Map<String, Object> result = new HashMap<>();
        List<Object> predictHistory = new ArrayList<>();

        predictHistory.add(new PredictBoardDto(
                new PlayerDto("망고망고", "https://naver.com", "admin@naver.com"),
                new PlayerDto("이싸피", "https://naver.com", "user1@naver.com"),
                true
        ));

        predictHistory.add(new PredictBoardDto(
                new PlayerDto("이싸피", "https://naver.com", "user1@naver.com"),
                new PlayerDto("김싸피", "https://naver.com", "user2@naver.com"),
                false
        ));

        predictHistory.add(new PredictBoardDto(
                new PlayerDto("김싸피", "https://naver.com", "user2@naver.com"),
                new PlayerDto("김도현", "https://naver.com", "user3@naver.com"),
                true
        ));

        predictHistory.add(new PredictBoardDto(
                new PlayerDto("김싸피", "https://naver.com", "user3@naver.com"),
                new PlayerDto("김도현", "https://naver.com", "admin1@naver.com"),
                true
        ));

        result.put("predict_history", predictHistory);


        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}/summary")
    public ResponseEntity<?> summaryManitoResult(@PathVariable Long roomId) {
        Map<String, Object> result = new HashMap<>();
        List<SummaryDto> summary_result = new ArrayList<>();

        summary_result.add(new SummaryDto(
                "가장 많이 받은 댓글은?",
                new SummaryResultData(
                        "집사는 오늘도 혼난다?",
                        "망고노예",
                        100000L,
                        "<img src='http://www.naver.com'> 제가 혼나고 삽니다.",
                        LocalDateTime.now(),
                        "https://www.nis.go.kr/main.do"
                )
        ));

        summary_result.add(new SummaryDto(
                "가장 많이 좋아요 받은 댓글은",
                new SummaryResultData(
                        "집사는 오늘도 혼난다?",
                        "망고노예",
                        100000L,
                        "<img src='http://www.naver.com'> 제가 혼나고 삽니다.",
                        LocalDateTime.now(),
                        "https://www.nis.go.kr/main.do"
                )
        ));

        summary_result.add(new SummaryDto(
                "가장 빨리 마니또를 맞춘 맴버는?",
                new SummaryResultData(
                        null,
                        "이싸피",
                        null,
                        null,
                        LocalDateTime.now(),
                        "https://www.nis.go.kr/main.do"
                )
        ));

        summary_result.add(new SummaryDto(
                "최고의 맴버는?",
                new SummaryResultData(
                        null,
                        "이싸피",
                        10000L,
                        null,
                        LocalDateTime.now(),
                        "https://www.nis.go.kr/main.do"
                )
        ));

        summary_result.add(new SummaryDto(
                "가장 많은 인증글을 작성한 맴버는?",
                new SummaryResultData(
                        null,
                        "김싸피",
                        null,
                        null,
                        LocalDateTime.now(),
                        "https://www.nis.go.kr/main.do"
                )
        ));

        result.put("summary_result", summary_result);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}/wordCloud")
    public ResponseEntity<?> searchManitoWorldCloud(@PathVariable Long roomId) {
        List<List<?>> result = historyQueryService.getWorldCloudContents(roomId);
        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 데이터를 로드하였습니다.", result);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{roomId}/manito/{userId}")
    public ResponseEntity<?> collectManitoResult(@PathVariable Long roomId,
                                                 @PathVariable String userId) {

        Map<String, Object> result = new HashMap<>();

        Map<String, Object> manito = new LinkedHashMap<>();
        Map<String, Object> maniti = new LinkedHashMap<>();

        manito.put("2024-01-24", new ArrayList<>());
        manito.put("2024-01-25",
                Arrays.stream(new Object[]{
                        new PostDto(123L, "내가 받은 거!", "contents", "2024-01-25T14:34", "반가요", 100L),
                        new PostDto(124L, "내가 받은 거!", "contents", "2024-01-25T14:35", "반가요", 100L),
                        new PostDto(125L, "내가 받은 거!", "contents", "2024-01-25T14:36", "반가요", 100L),
                }).map(d -> new AbstractMap.SimpleEntry<>(d.getClass().getSimpleName().substring(0, d.getClass().getSimpleName().length()- 3).toLowerCase(), d))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new))
        );
        manito.put("2024-01-26",
                Arrays.stream(new Object[]{
                        new PostDto(136L, "내가 받은 거!", "contents", "2024-01-26T14:34", "반가요", 100L),
                        new PredictorDto(1L, "2024-01-26T14:58", "반가워요", "김치가제일싫어")
                }).map(d -> new AbstractMap.SimpleEntry<>(d.getClass().getSimpleName().substring(0, d.getClass().getSimpleName().length()- 3).toLowerCase(), d))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new))
        );

        maniti.put("2024-01-24",
                Arrays.stream(new PostDto[]{
                        new PostDto(123L, "마니띠가 해준 거!", "contents", "2024-01-25T14:34", "반가요", 100L),
                        new PostDto(123L, "마니띠가 해준 거!", "contents", "2024-01-25T14:35", "반가요", 100L),
                        new PostDto(123L, "마니띠가 해준 거!", "contents", "2024-01-25T14:36", "반가요", 100L),
                        new PostDto(123L, "마니띠가 해준 거!", "contents", "2024-01-25T14:37", "반가요", 100L),
                }).map(d -> new AbstractMap.SimpleEntry<>(d.getClass().getSimpleName().substring(0, d.getClass().getSimpleName().length()- 3).toLowerCase(), d))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new))
        );
        maniti.put("2024-01-25",
                Arrays.stream(new PostDto[]{
                        new PostDto(123L, "마니띠가 해준 거!", "contents", "2024-01-25T14:34", "반가요", 100L),
                        new PostDto(124L, "마니띠가 해준 거!", "contents", "2024-01-25T14:35", "반가요", 100L),
                }).map(d -> new AbstractMap.SimpleEntry<>(d.getClass().getSimpleName().substring(0, d.getClass().getSimpleName().length()- 3).toLowerCase(), d))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new))
        );
        maniti.put("2024-01-26",
                Arrays.stream(new Object[]{
                        new PostDto(136L, "마니띠가 해줬어!", "contents", "2024-01-26T14:34", "반가요", 100L),
                        new PredictorDto(1L, "2024-01-26T14:58", "나", "김치가제일싫어")
                }).map(d -> new AbstractMap.SimpleEntry<>(d.getClass().getSimpleName().substring(0, d.getClass().getSimpleName().length()- 3).toLowerCase(), d))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new))
        );


        result.put("manito", manito);
        result.put("maniti", maniti);

        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 데이터를 조회했습니다.", result);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{roomId}/maniti/{userId}")
    public ResponseEntity<?> collectManitiResult(@PathVariable Long roomId,
                                                 @PathVariable String userId) {

        Map<String, Object> result = new HashMap<>();

        Map<String, Object> maniti = new LinkedHashMap<>();
        Map<String, Object> manito = new LinkedHashMap<>();

        maniti.put("2024-01-24", new ArrayList<>());
        maniti.put("2024-01-25",
                Arrays.stream(new Object[]{
                                new PostDto(123L, "내가 해준 거!", "contents", "2024-01-25T14:34", "반가요", 100L),
                                new PostDto(124L, "내가 해준 거!", "contents", "2024-01-25T14:35", "반가요", 100L),
                                new PostDto(125L, "내가 해준 거!", "contents", "2024-01-25T14:36", "반가요", 100L),
                        }).map(d -> new AbstractMap.SimpleEntry<>(d.getClass().getSimpleName().substring(0, d.getClass().getSimpleName().length()- 3).toLowerCase(), d))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new))
        );
        maniti.put("2024-01-26",
                Arrays.stream(new Object[]{
                                new PostDto(136L, "내가 해줬어!", "contents", "2024-01-26T14:34", "반가요", 100L),
                        }).map(d -> new AbstractMap.SimpleEntry<>(d.getClass().getSimpleName().substring(0, d.getClass().getSimpleName().length()- 3).toLowerCase(), d))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new))
        );

        manito.put("2024-01-24",
                Arrays.stream(new PostDto[]{
                                new PostDto(123L, "마니띠가 해준 거!", "contents", "2024-01-25T14:34", "반가요", 100L),
                                new PostDto(123L, "마니띠가 해준 거!", "contents", "2024-01-25T14:35", "반가요", 100L),
                                new PostDto(123L, "마니띠가 해준 거!", "contents", "2024-01-25T14:36", "반가요", 100L),
                                new PostDto(123L, "마니띠가 해준 거!", "contents", "2024-01-25T14:37", "반가요", 100L),
                        }).map(d -> new AbstractMap.SimpleEntry<>(d.getClass().getSimpleName().substring(0, d.getClass().getSimpleName().length()- 3).toLowerCase(), d))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new))
        );
        manito.put("2024-01-25",
                Arrays.stream(new PostDto[]{
                                new PostDto(123L, "마니띠가 해준 거!", "contents", "2024-01-25T14:34", "반가요", 100L),
                                new PostDto(124L, "마니띠가 해준 거!", "contents", "2024-01-25T14:35", "반가요", 100L),
                        }).map(d -> new AbstractMap.SimpleEntry<>(d.getClass().getSimpleName().substring(0, d.getClass().getSimpleName().length()- 3).toLowerCase(), d))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new))
        );
        manito.put("2024-01-26",
                Arrays.stream(new Object[]{
                                new PostDto(136L, "마니띠가 해줬어!", "contents", "2024-01-26T14:34", "반가요", 100L),
                                new PredictorDto(1L, "2024-01-26T14:58", "반가워요", "김치가제일싫어")
                        }).map(d -> new AbstractMap.SimpleEntry<>(d.getClass().getSimpleName().substring(0, d.getClass().getSimpleName().length()- 3).toLowerCase(), d))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (x, y) -> y, LinkedHashMap::new))
        );


        result.put("manito", manito);
        result.put("maniti", maniti);

        SuccessResponse response = new SuccessResponse(HttpStatus.OK, "정상적으로 데이터를 조회했습니다.", result);

        return ResponseEntity.ok(response);
    }
}

class A {
    LocalDateTime ab;

    @Override
    public String toString() {
        return "A{" +
                "ab=" + ab +
                '}';
    }

    public A(LocalDateTime ab) {
        this.ab = ab;
    }
}

// 클래스 B
class B {
    LocalDateTime dfd;

    @Override
    public String toString() {
        return "B{" +
                "dfd=" + dfd +
                '}';
    }

    public B(LocalDateTime dfd) {
        this.dfd = dfd;
    }
}


class Tests {
    void testing() {

        List<Object> list = new ArrayList<>();
        list.add(new A(LocalDateTime.parse("2024-01-24T12:23")));
        list.add(new A(LocalDateTime.parse("2024-01-24T12:24")));
        list.add(new B(LocalDateTime.parse("2024-01-24T12:22")));
        list.add(new B(LocalDateTime.parse("2024-01-26T12:22")));
        list.add(new A(LocalDateTime.parse("2024-01-25T13:23")));
        list.add(new A(LocalDateTime.parse("2024-01-28T14:23")));
        list.add(new B(LocalDateTime.parse("2024-01-27T12:12")));


        Map<LocalDate, List<Object>> groupedByDate = list.stream()
                .collect(TreeMap::new,
                        (map, obj) -> {
                            LocalDate date;
                            if (obj instanceof A) {
                                date = ((A) obj).ab.toLocalDate();
                            } else if (obj instanceof B) {
                                date = ((B) obj).dfd.toLocalDate();
                            } else {
                                throw new IllegalArgumentException("Unsupported type: " + obj.getClass());
                            }

                            map.computeIfAbsent(date, k -> new ArrayList<>() {
                            }).add(obj);
                        },
                        TreeMap::putAll);

        System.out.println(groupedByDate.toString());
    }
}
