package com.pjg.secreto.room.command.service;

import com.pjg.secreto.room.command.dto.*;

public interface RoomCommandService {
    CreateRoomResponseDto createRoom(CreateRoomRequestDto createRoomRequestDto);

    void changeRoomName(ChangeRoomNameRequestDto changeRoomNameRequestDto);

    SetRoomResponseDto setRoom(SetRoomRequestDto setRoomRequestDto);

    Long enterRoom(EnterRoomRequestDto enterRoomRequestDto);

    void acceptUser(AcceptUserRequestDto acceptUserRequestDto);

    void deligateAdmin(DeligateAdminRequestDto deligateAdminRequestDto);

    void bookmarkRoom(BookmarkRoomRequestDto bookmarkRoomRequestDto);

    void terminateRoom(TerminateRoomRequestDto terminateRoomRequestDto);

    void denyUser(DenyUserRequestDto denyUserRequestDto);

    void exitRoom(ExitRoomRequestDto exitRoomRequestDto);

    void initMatching(InitMatchingRequestDto initMatchingRequestDto);

    void insertMatching(InsertMatchingRequestDto insertMatchingRequestDto);
}

