package com.pjg.secreto.board.query.repository;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.Liked;
import com.pjg.secreto.room.common.entity.RoomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface LikedQueryRepository extends JpaRepository<Liked, Long>, JpaSpecificationExecutor<Liked>  {
    Optional<Liked> findLikedByBoardAndRoomUser(Board board, RoomUser roomUser);
}
