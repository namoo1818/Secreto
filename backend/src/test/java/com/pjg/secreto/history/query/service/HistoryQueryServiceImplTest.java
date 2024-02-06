package com.pjg.secreto.history.query.service;

import com.pjg.secreto.history.query.dto.BestMemberDto;
import com.pjg.secreto.history.query.dto.SummaryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HistoryQueryServiceImplTest {
    @Autowired
    HistoryQueryService historyQueryService;

    @Test
    void test1(){
        List<SummaryDto> manitoStaticResult = historyQueryService.getManitoStaticResult(1L);

        manitoStaticResult.stream()
                .forEach(System.out::println);
    }

}