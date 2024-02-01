package com.pjg.secreto.user.common.Repository;

import com.pjg.secreto.user.common.entity.PasswordCheck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PasswordCheckRepositoryTest {

    @Autowired
    PasswordCheckRepository passwordCheckRepository;

    @Test
    void test(){
        PasswordCheck passwordCheck = PasswordCheck.builder().email("admin1111@naver.com").validationCode("123123213").build();
        PasswordCheck save = passwordCheckRepository.save(passwordCheck);


        Optional<PasswordCheck> byEmail = passwordCheckRepository.findById("123123213");

        Assertions.assertTrue(byEmail.isPresent());
    }

}