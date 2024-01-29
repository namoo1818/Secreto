package com.pjg.secreto.user.command.repository;

import com.pjg.secreto.user.common.entity.PasswordCheck;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordCheckQueryRepository extends CrudRepository<PasswordCheck, String> {
    Optional<PasswordCheck> findByEmail(String email);
}
