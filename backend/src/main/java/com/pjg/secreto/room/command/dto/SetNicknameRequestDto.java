package com.pjg.secreto.room.command.dto;

import lombok.Data;

@Data
public class SetNicknameRequestDto {

    private Long roomUserNo;

    private String nickname;
}
