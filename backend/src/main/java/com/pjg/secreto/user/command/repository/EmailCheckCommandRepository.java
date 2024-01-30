package com.pjg.secreto.user.command.repository;

import com.pjg.secreto.user.common.entity.EmailCheck;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailCheckCommandRepository extends CrudRepository<EmailCheck, String> {
    Optional<EmailCheck> findByEmail(String email);
}
