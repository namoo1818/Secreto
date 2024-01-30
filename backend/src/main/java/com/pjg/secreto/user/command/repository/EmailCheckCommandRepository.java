package com.pjg.secreto.user.command.repository;

import com.pjg.secreto.user.common.entity.EmailCheck;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailCheckCommandRepository extends CrudRepository<EmailCheck, String> {
    Optional<EmailCheck> findByEmail(String email);
}
