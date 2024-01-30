package com.pjg.secreto.board.query.service;

import com.pjg.secreto.board.query.repository.BoardQueryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardQueryServiceImplTest {
    @Autowired
    BoardQueryRepository boardQueryRepository;

    @Autowired
    BoardQueryService boardQueryService;

    @Autowired
    PostQueryRepository postQueryRepository;



    @Test
    void getBoardBySpecification() {
    }

    @Test
    void getPost() {
        SearchPostResponseDto post = boardQueryService.getPost(1L);
        Assertions.assertNotNull(post);
        System.out.println(post.getRoomUser());
    }

    @Test
    void getRely() {
    }
}