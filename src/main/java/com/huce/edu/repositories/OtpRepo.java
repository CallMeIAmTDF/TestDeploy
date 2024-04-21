package com.huce.edu.repositories;

import com.huce.edu.entities.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OtpRepo extends JpaRepository<OtpEntity, Integer> {
//    OtpEntity findFirstByOtpCode(String code);
//    OtpEntity findFirstByEmail(String email);
    OtpEntity findFirstByUid(int userId);



//    boolean existsByEmail(String email);
}
