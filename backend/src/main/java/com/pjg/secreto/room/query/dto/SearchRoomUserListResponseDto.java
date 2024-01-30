package com.pjg.secreto.room.query.dto;

import com.pjg.secreto.room.common.entity.Room;
import com.pjg.secreto.room.common.entity.RoomUser;
import com.pjg.secreto.user.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Builder
public class SearchRoomUserListResponseDto {

    private Long roomUserNo;

    private Long userNo;

    private Long roomNo;

    private LocalDateTime userEntryAt;

    private LocalDateTime userLeaveAt;

    private Boolean standbyYn;

    private String nickname;

    public static SearchRoomUserListResponseDto of(RoomUser roomUser) {
        return SearchRoomUserListResponseDto.builder()
                .roomUserNo(roomUser.getId())
                .userNo(roomUser.getUser().getId())
                .roomNo(roomUser.getRoom().getId())
                .userEntryAt(roomUser.getUserEntryAt())
                .userLeaveAt(roomUser.getUserLeaveAt())
                .standbyYn(roomUser.getStandbyYn())
                .nickname(roomUser.getNickname())
                .build();
    }
}
