package com.pjg.secreto.history.command.repository;

import com.pjg.secreto.history.common.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchingCommandRepository extends JpaRepository<Matching, Long> {
}
