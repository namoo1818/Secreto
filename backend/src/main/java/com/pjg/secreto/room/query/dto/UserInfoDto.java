package com.pjg.secreto.room.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDto {

    private Long roomUserNo;
    private String nickname;
    private String profileUrl;
}
