package com.huce.edu.repositories;

import com.huce.edu.entities.VerificationcodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VerificationCodeRepo extends JpaRepository<VerificationcodeEntity, Integer> {
    VerificationcodeEntity findFirstByCode(String code);
    VerificationcodeEntity findFirstByEmail(String email);
    boolean existsByEmail(String email);

}
