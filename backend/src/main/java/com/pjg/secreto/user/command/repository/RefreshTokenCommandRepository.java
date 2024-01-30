package com.pjg.secreto.user.command.repository;

import com.pjg.secreto.user.common.entity.RefreshToken;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableRedisRepositories
public interface RefreshTokenCommandRepository extends CrudRepository<RefreshToken, String> {

}
