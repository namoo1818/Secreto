package com.pjg.secreto.board.query.repository;

import com.pjg.secreto.board.common.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardQueryRepository extends JpaRepository<Board, Long>, JpaSpecificationExecutor<Board> {
    Page<Board> findAll(Specification<Board> spec, Pageable pageable);

    Optional<Board> findById(Long id);
}
