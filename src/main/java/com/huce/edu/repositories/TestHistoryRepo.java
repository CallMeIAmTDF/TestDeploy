package com.huce.edu.repositories;

import com.huce.edu.entities.TestHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestHistoryRepo extends JpaRepository<TestHistoryEntity, Integer> {
    List<TestHistoryEntity> findByUid(int uid);


}