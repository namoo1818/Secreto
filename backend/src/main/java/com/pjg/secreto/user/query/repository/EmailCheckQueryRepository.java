package com.pjg.secreto.user.query.repository;

import com.pjg.secreto.user.common.entity.EmailCheck;
import com.pjg.secreto.user.common.entity.RefreshToken;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailCheckQueryRepository extends CrudRepository<EmailCheck, String> {
    Optional<EmailCheck> findByEmail(String email);
}
