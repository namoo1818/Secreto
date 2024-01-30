package com.pjg.secreto.history.query.dto;

import com.pjg.secreto.room.common.entity.RoomUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class PlayerDto {
    private String nickname;
    private String profileUrl;
    private String email;

    public PlayerDto(RoomUser roomUser){
        this.email = roomUser.getUser().getEmail();
        this.profileUrl = roomUser.getUser().getProfileUrl();
        this.nickname = roomUser.getNickname();
    }
}
