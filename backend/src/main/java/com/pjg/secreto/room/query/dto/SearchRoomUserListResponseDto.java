package com.pjg.secreto.room.query.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class SearchRoomUserListResponseDto {

    private Long roomUserNO;

    private Long userNo;

    private Long roomNo;

    private LocalDateTime userEntryAt;

    private LocalDateTime userLeaveAt;

    private Boolean standbyYn;

    private Long usersManito;

    private Long usersManiti;

    private String nickname;

}
