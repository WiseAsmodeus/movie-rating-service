package com.movie.web.repositories;

import com.movie.web.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsernameOrEmail(String username, String email);
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
}
