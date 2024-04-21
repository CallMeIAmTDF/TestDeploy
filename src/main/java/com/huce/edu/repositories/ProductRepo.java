package com.huce.edu.repositories;


import com.huce.edu.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Integer> {
    ProductEntity findFirstByPid(Integer pid);
    ProductEntity findFirstByName(String name);
    boolean existsByName(String name);
}

