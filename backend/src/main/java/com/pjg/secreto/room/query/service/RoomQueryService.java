package com.pjg.secreto.room.query.service;

import com.pjg.secreto.room.query.dto.CheckCodeDto;
import com.pjg.secreto.room.query.dto.SearchRoomListResponseDto;
import com.pjg.secreto.room.query.dto.SearchRoomResponseDto;
import com.pjg.secreto.room.query.dto.SearchRoomUserListResponseDto;

import java.sql.SQLException;
import java.util.List;

public interface RoomQueryService {
    List<SearchRoomUserListResponseDto> searchRoomUserList(Long roomNo);

    List<SearchRoomListResponseDto> searchRoomList(Long userNo);

    SearchRoomResponseDto searchRoom(Long userNo, Long roomNo);

    boolean enterRoom(CheckCodeDto checkCodeDto);
}
