package com.pjg.secreto.user.common.Repository;

import com.pjg.secreto.user.common.entity.PasswordCheck;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasswordCheckRepository extends CrudRepository<PasswordCheck, String> {
    Optional<PasswordCheck> findByEmail(String email);
}
