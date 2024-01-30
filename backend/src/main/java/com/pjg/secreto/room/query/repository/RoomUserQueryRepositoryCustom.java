package com.pjg.secreto.room.query.repository;

import com.pjg.secreto.room.query.dto.SearchRoomUserListResponseDto;

import java.util.List;

public interface RoomUserQueryRepositoryCustom {

    List<SearchRoomUserListResponseDto> findAllWithMatching();
}
