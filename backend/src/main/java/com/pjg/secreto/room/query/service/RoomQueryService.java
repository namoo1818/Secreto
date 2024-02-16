package com.pjg.secreto.room.query.service;

import com.pjg.secreto.room.query.dto.*;

import java.sql.SQLException;
import java.util.List;

public interface RoomQueryService {
    List<SearchRoomUserListResponseDto> searchRoomUserList(Long userNo, Long roomNo);

    List<SearchRoomListResponseDto> searchRoomList(Long userNo);

    SearchRoomResponseDto searchRoom(Long userNo, Long roomNo);

    boolean enterRoom(CheckCodeDto checkCodeDto);

    List<ShowMatchingLogsResponseDto> showMatchingLogs(Long userNo, Long roomNo);
}
