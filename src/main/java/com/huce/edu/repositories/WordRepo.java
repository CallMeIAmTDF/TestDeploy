package com.huce.edu.repositories;

import com.huce.edu.entities.WordEntity;
import com.huce.edu.models.mapper.WordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface WordRepo extends JpaRepository<WordEntity, Integer> {
    WordEntity findByWid(Integer wid);
    boolean existsByTid(Integer tid);
    WordEntity findFirstByWid(Integer wid);
    Collection<WordId> getByTidIn(List<Integer> tids);
    boolean existsByWid(Integer wid);
    List<WordEntity> findByWord(String word);
    List<WordEntity> findByTid(Integer tid);
}

