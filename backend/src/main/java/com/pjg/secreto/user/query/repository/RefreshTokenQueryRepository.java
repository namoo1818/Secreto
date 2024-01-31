package com.pjg.secreto.user.query.repository;

import com.pjg.secreto.user.common.entity.RefreshToken;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RefreshTokenQueryRepository extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByEmail(String email);
}
