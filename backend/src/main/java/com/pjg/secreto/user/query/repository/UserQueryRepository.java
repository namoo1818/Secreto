package com.pjg.secreto.user.query.repository;

import com.pjg.secreto.user.common.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserQueryRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndAndNickname(String email, String nickName);

    Optional<User> findByEmailAndPassword(String email, String password);

    @Query("select u from User u join fetch u.roomUsers ru join fetch ru.room r where r.hostNo = :hostNo")
    User findByhostNo(Long hostNo);
}
