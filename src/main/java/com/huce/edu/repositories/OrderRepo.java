package com.huce.edu.repositories;

import com.huce.edu.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Integer> {
    OrderEntity findByOid(Integer oid);
    List<OrderEntity> findByUid(Integer uid);
}
