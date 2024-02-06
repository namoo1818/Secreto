package com.pjg.secreto.history.command.repository;

import com.pjg.secreto.history.common.entity.ManitoExpectLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManitoExpectLogCommandRepository extends JpaRepository<ManitoExpectLog, Long> {
}
