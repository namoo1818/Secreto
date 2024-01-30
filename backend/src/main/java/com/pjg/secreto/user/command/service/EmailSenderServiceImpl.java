package com.pjg.secreto.user.command.service;

import com.pjg.secreto.history.command.dto.EMailRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    private final MailProperties properties;
    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(EMailRequestDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(properties.getUsername());
        message.setTo(dto.getTo());
        message.setSubject(dto.getSubject());
        message.setText(dto.getContents());

        javaMailSender.send(message);

    }
}
