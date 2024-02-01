package com.pjg.secreto.board.query.repository;

import com.pjg.secreto.board.common.entity.Board;
import com.pjg.secreto.board.common.entity.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardQueryRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {
    List<Board> findBoardByBoardCategory(BoardCategory boardCategory);

    List<Board> findBoardByBoardCategoryAndTitleContaining(BoardCategory boardCategory, String title);
    List<Board> findBoardByBoardCategoryAndContentContaining(BoardCategory boardCategory, String content);
    List<Board> findBoardByBoardCategoryAndWriterContaining(BoardCategory boardCategory, String writer);


    Optional<Board> findById(Long boardNo);
}
