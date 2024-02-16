package com.pjg.secreto.board.query.repository;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardEntryLog;
import com.pjg.secreto.room.common.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BoardEntryLogQueryRepository extends JpaRepository<BoardEntryLog, Long> {
    Optional<BoardEntryLog> findByBoardAndAndRoomUserAndAndEntryAt(Board board, RoomUser roomUser, LocalDate entryAt);
}
