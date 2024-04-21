package com.huce.edu.repositories;

import com.huce.edu.entities.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface TopicRepo extends JpaRepository<TopicEntity, Integer> {
    TopicEntity findByTopicAndLid(String name, Integer tid);
    TopicEntity findFirstByTid(Integer tid);
    boolean existsByTid(Integer tid);
    boolean existsByTopicAndLid(String name, Integer tid);
    boolean existsByLid(Integer lid);
    boolean existsByTopic(String name);
    ArrayList<TopicEntity> findByLid(Integer lid);
    TopicEntity findByTopic(String id);
}