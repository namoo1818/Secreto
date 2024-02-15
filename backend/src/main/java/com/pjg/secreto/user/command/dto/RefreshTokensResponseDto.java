package com.pjg.secreto.user.command.dto;

import com.pjg.secreto.user.common.entity.User;
import lombok.Data;

@Data
public class RefreshTokensResponseDto {
    private String email;
    private String userNo;
    private String accessToken;
    private String refreshToken;

    public RefreshTokensResponseDto(User user,
                                    String accessToken,
                                    String refreshToken){
        this.email = user.getEmail();
        this.userNo = user.getId().toString();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
