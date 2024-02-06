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
    Page<Board> findBoardByBoardCategory(BoardCategory boardCategory, Pageable pageable);

    @Query("select b from Board b where b.boardCategory = :boardCategory and b.title like %:title%")
    Page<Board> findBoardByBoardCategoryAndTitleContaining(@Param("boardCategory") BoardCategory boardCategory,
                                                            @Param("title") String title,
                                                            Pageable pageable);

    @Query("select b from Board b where b.boardCategory = :boardCategory and b.title like %:content%")
    Page<Board> findBoardByBoardCategoryAndContentContaining(@Param("boardCategory") BoardCategory boardCategory,
                                                           @Param("content") String content,
                                                           Pageable pageable);

    @Query("select b from Board b where b.boardCategory = :boardCategory and b.title like %:writer%")
    Page<Board> findBoardByBoardCategoryAndWriterContaining(@Param("boardCategory") BoardCategory boardCategory,
                                                             @Param("writer") String writer,
                                                             Pageable pageable);

    Optional<Board> findById(Long boardNo);
}
