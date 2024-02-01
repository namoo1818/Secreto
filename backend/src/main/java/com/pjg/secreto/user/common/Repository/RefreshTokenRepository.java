package com.pjg.secreto.user.common.Repository;

import com.pjg.secreto.user.common.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    void deleteById(String id);
}
