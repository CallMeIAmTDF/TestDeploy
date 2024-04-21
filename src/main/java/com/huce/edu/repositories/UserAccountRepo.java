package com.huce.edu.repositories;

import com.huce.edu.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepo extends JpaRepository<UserEntity, Integer> {
    boolean existsByEmail(String email);
    UserEntity findFirstByUid(int id);
    UserEntity findFirstByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

}
