package com.pjg.secreto.user.command.service;

import com.pjg.secreto.history.command.dto.EMailRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailSenderServiceTest {
    @Autowired
    EmailSenderService service;

    @Test
    void testSending(){
        EMailRequestDto request= new EMailRequestDto("안녕하세요",
                "kjsw12@naver.com",
                "pjg.secreto@gmail.com",
                "테스트 입니다.");

        service.sendMail(request);
    }

}