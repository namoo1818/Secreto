package com.pjg.secreto.user.command.service;

import com.pjg.secreto.user.command.dto.JoinRequestDto;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserCommandService {
    User register(ProviderUser target);

    User register(JoinRequestDto dto);

    String refreshToken(String refreshToken);
}
