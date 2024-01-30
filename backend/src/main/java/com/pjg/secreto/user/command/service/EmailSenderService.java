package com.pjg.secreto.user.command.service;


import com.pjg.secreto.history.command.dto.EMailRequestDto;

public interface EmailSenderService {
    void sendMail(EMailRequestDto dto);
}
