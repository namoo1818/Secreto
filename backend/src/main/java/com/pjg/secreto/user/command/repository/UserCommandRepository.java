package com.pjg.secreto.user.command.repository;

import com.pjg.secreto.user.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCommandRepository extends JpaRepository<User, Long> {
}
