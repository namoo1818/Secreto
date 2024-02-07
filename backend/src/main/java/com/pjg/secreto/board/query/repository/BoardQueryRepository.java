package com.pjg.secreto.board.query.repository;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardCategory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardQueryRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {
    @Query("select b from Board b join fetch b.roomUser ru where ru.room.id = :roomNo and b.boardCategory = :boardCategory")
    Page<Board> findByBoardCategory(@Param("boardCategory") BoardCategory boardCategory,
                                    @Param("roomNo") Long roomNo,
                                    Pageable pageable);

    @Query("select b from Board b join fetch b.roomUser ru where ru.room.id = :roomNo and b.boardCategory = :boardCategory and b.title like %:title%")
    Page<Board> findBytitle(@Param("boardCategory") BoardCategory boardCategory,
                                                            @Param("title") String title,
                                                            @Param("roomNo") Long roomNo,
                                                            Pageable pageable);

    @Query("select b from Board b join fetch b.roomUser ru where ru.room.id = :roomNo and b.boardCategory = :boardCategory and b.content like %:content%")
    Page<Board> findBycontent(@Param("boardCategory") BoardCategory boardCategory,
                                                           @Param("content") String content,
                                                           @Param("roomNo") Long roomNo,
                                                           Pageable pageable);

    @Query("select b from Board b join fetch b.roomUser ru where ru.room.id = :roomNo and b.boardCategory = :boardCategory and b.writer like %:writer%")
    Page<Board> findBywriter(@Param("boardCategory") BoardCategory boardCategory,
                                                             @Param("writer") String writer,
                                                             @Param("roomNo") Long roomNo,
                                                             Pageable pageable);

    Optional<Board> findById(Long boardNo);
}
