package com.pjg.secreto.board.query.repository;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardCategory;
import com.pjg.secreto.mission.common.entity.UserMission;
import com.pjg.secreto.room.common.entity.RoomUser;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardQueryRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {
    @Query("select b from Board b join fetch b.roomUser ru where ru.room.id = :roomNo and b.boardCategory = :boardCategory and (b.publicYn = true or b.roomUser.id = :roomUserNo)")
    Page<Board> findByBoardCategory(@Param("boardCategory") BoardCategory boardCategory,
                                    @Param("roomNo") Long roomNo,
                                    @Param("roomUserNo") Long roomUserNo,
                                    Pageable pageable);

    @Query("select b from Board b join fetch b.roomUser ru where ru.room.id = :roomNo and b.boardCategory = :boardCategory and b.title like %:title% and (b.publicYn = true or b.roomUser.id = :roomUserNo)")
    Page<Board> findBytitle(@Param("boardCategory") BoardCategory boardCategory,
                                                            @Param("title") String title,
                                                            @Param("roomNo") Long roomNo,
                                                            @Param("roomUserNo") Long roomUserNo,
                                                            Pageable pageable);

    @Query("select b from Board b join fetch b.roomUser ru where ru.room.id = :roomNo and b.boardCategory = :boardCategory and b.content like %:content% and (b.publicYn = true or b.roomUser.id = :roomUserNo)")
    Page<Board> findBycontent(@Param("boardCategory") BoardCategory boardCategory,
                                                           @Param("content") String content,
                                                           @Param("roomNo") Long roomNo,
                                                           @Param("roomUserNo") Long roomUserNo,
                                                           Pageable pageable);

    @Query("select b from Board b join fetch b.roomUser ru where ru.room.id = :roomNo and b.boardCategory = :boardCategory and ru.nickname like %:writer% and (b.publicYn = true or b.roomUser.id = :roomUserNo)")
    Page<Board> findBywriter(@Param("boardCategory") BoardCategory boardCategory,
                                                             @Param("writer") String writer,
                                                             @Param("roomNo") Long roomNo,
                                                             @Param("roomUserNo") Long roomUserNo,
                                                             Pageable pageable);

    @Query("select b from Board b join fetch b.roomUser ru join fetch ru.matchings m where ru.room.id = :roomNo and b.boardCategory = :boardCategory " +
            "and ru.id = m.manitiNo and ru.nickname like %:writer% and (b.publicYn = true or b.roomUser.id = :roomUserNo)")
    Page<Board> findBymaniti(@Param("boardCategory") BoardCategory boardCategory,
                             @Param("writer") String writer,
                             @Param("roomNo") Long roomNo,
                             @Param("roomUserNo") Long roomUserNo,
                             Pageable pageable);

    Optional<Board> findById(Long boardNo);

    Optional<Board> findBoardByUserMissionAndRoomUser(UserMission userMission, RoomUser roomUser);
}
