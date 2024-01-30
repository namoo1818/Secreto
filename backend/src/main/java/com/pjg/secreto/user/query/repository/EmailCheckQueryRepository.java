package com.pjg.secreto.user.query.repository;

import com.pjg.secreto.user.common.entity.EmailCheck;
import com.pjg.secreto.user.common.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailCheckQueryRepository extends CrudRepository<EmailCheck, String> {
    Optional<EmailCheck> findByEmail(String email);
}
