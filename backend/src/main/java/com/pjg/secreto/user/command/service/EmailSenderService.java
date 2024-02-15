package com.pjg.secreto.user.command.service;


import com.pjg.secreto.user.common.dto.EmailSendRequestDto;

public interface EmailSenderService {
    void sendMail(EmailSendRequestDto dto);
}
