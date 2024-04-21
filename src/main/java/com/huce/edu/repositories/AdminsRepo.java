package com.huce.edu.repositories;

import com.huce.edu.entities.AdminsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AdminsRepo extends JpaRepository<AdminsEntity, Integer> {

    AdminsEntity findFirstByEmail(String email);

    Optional<AdminsEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    AdminsEntity findByAid(Integer aid);

    AdminsEntity findByName(String name);


}

