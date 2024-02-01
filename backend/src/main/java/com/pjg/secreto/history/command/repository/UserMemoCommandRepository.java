package com.pjg.secreto.history.command.repository;

import com.pjg.secreto.history.common.entity.UserMemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMemoCommandRepository extends JpaRepository<UserMemo, Long> {
}
