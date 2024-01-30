package com.pjg.secreto.user.query.service;

import com.pjg.secreto.user.common.entity.User;
import com.pjg.secreto.user.query.dto.LoginRequestDto;
import com.pjg.secreto.user.query.dto.LoginResponseDto;


public interface UserQueryService {
    User findByEmail(String email);

    User findById(Long id);

    LoginResponseDto login(LoginRequestDto dto);
}
