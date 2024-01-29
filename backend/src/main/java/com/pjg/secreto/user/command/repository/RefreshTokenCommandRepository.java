package com.pjg.secreto.user.command.repository;

import com.pjg.secreto.user.common.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenCommandRepository extends CrudRepository<RefreshToken, String> {

}
