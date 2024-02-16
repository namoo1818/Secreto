package com.pjg.secreto.user.common.Repository;

import com.pjg.secreto.user.common.entity.EmailCheck;
import com.pjg.secreto.user.common.entity.PasswordCheck;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailCheckRepository extends CrudRepository<EmailCheck, String> {
    Optional<EmailCheck> findByEmail(String email);

    void deleteByEmail(String email);
}
