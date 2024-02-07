//package com.pjg.secreto.history.query.repository;
//
//import com.pjg.secreto.history.query.dto.SummaryResultData;
//import com.querydsl.core.Tuple;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class StaticRepositoryTest {
//    @Autowired
//    ManitoExpectRepository manitoExpectRepository;
//
//    @Autowired
//    StaticRepository staticRepository;
//
//    @Test
//    void test1(){
//        SummaryResultData mostLikedPost = staticRepository.getMostLikedPost(1L);
//        System.out.println(mostLikedPost);
//    }
//
//    @Test
//    void test2(){
//        SummaryResultData mostWroteCerticationUser = staticRepository.getMostWroteCerticationUser(1L);
//        System.out.println(mostWroteCerticationUser);
//    }
//    @Test
//    void test3(){
//        SummaryResultData fastestCorrectManito = manitoExpectRepository.getFastestCorrectManito(1L);
//        System.out.println(fastestCorrectManito);
//    }
//}