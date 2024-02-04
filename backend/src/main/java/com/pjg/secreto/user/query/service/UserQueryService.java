package com.pjg.secreto.user.query.service;

import com.pjg.secreto.user.common.dto.UserInfo;
import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.query.dto.LogOutRequestDto;
import com.pjg.secreto.user.query.dto.LoginRequestDto;
import com.pjg.secreto.user.query.dto.LoginResponseDto;
import com.pjg.secreto.user.query.dto.ValidateCertRequestDto;
import org.springframework.security.core.Authentication;


public interface UserQueryService {
    User findByEmail(String email);

    User findById(Long id);

    LoginResponseDto login(LoginRequestDto dto);

    void logOut(LogOutRequestDto dto);

    UserInfo detail(Authentication authentication);

    String allowChangePassword(String certCode);

    boolean validateDuplicatedEmail(ValidateCertRequestDto dto);
}
