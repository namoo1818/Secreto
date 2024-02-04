package com.pjg.secreto.user.common.Repository;

import com.pjg.secreto.user.common.entity.EmailConfirm;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmailConfirmRepository extends CrudRepository<EmailConfirm, String> {
    Optional<EmailConfirm> findByEmail(String email);

    void deleteByEmail(String email);
}
