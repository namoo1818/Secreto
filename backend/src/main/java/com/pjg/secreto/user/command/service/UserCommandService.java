package com.pjg.secreto.user.command.service;

import com.pjg.secreto.user.command.dto.*;
import com.pjg.secreto.user.common.dto.EmailValidationResponseDto;
import com.pjg.secreto.user.common.dto.ProviderUser;
import com.pjg.secreto.user.common.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface UserCommandService {
    User register(ProviderUser target);

    User register(JoinRequestDto dto);

    RefreshTokensResponseDto refreshToken(String refreshToken);

    void withdraw(WithdrawRequestDto dto, Authentication authentication);

    User modify(ModifyRequestDto dto, Authentication authentication);

    void sendPasswordChangeEmail(ChangePasswordRequestDto dto);

    void resetPassword(PasswordResetRequestDto dto);

    void changePassword(ChangeLegacyPasswordRequestDto dto);

    EmailValidationResponseDto sendEmailValidationMail(String userId);
}
