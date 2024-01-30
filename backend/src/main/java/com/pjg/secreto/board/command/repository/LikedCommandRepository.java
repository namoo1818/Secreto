package com.pjg.secreto.board.command.repository;

import com.pjg.secreto.board.common.entity.Liked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikedCommandRepository extends JpaRepository<Liked, Long> {
}
 