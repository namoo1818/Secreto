package com.pjg.secreto.board.query.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class BoardQueryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BoardQueryController queryController;

    @Test
    void readBoard() {
    }

    @Test
    void readPost() {

    }

    @Test
    void readReply() {
    }
}