package com.pjg.secreto.user.command.service;

import com.pjg.secreto.user.common.dto.EmailSendRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {
    private final MailProperties properties;
    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(EmailSendRequestDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(properties.getUsername());
        message.setTo(dto.getTo());
        message.setSubject(dto.getSubject());
        message.setText(dto.getContents());

        MimeMessagePreparator preparatory = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            String content = dto.getContents();
            helper.setTo(dto.getTo());
            helper.setFrom(dto.getFrom());
            helper.setSubject(dto.getSubject());
            helper.setText(content, true); //html 타입이므로, 두번째 파라미터에 true 설정
        };

        javaMailSender.send(preparatory);
    }


}
