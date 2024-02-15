package com.pjg.secreto.board.command.repository;

import com.pjg.secreto.board.common.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardCommandRepository extends JpaRepository<Board, Long> {
}
