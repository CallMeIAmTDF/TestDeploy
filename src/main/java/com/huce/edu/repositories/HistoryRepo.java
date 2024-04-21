package com.huce.edu.repositories;

import com.huce.edu.entities.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface HistoryRepo extends JpaRepository<HistoryEntity, Integer> {
    HistoryEntity findFirstById(Integer id);

    ArrayList<HistoryEntity> findByUid(Integer uid);
    ArrayList<HistoryEntity> findByUidAndIscorrectAndWidIn(Integer uid, Integer isCorrect, List<Integer> wid);
    ArrayList<HistoryEntity> findByIscorrect(Integer isCorrect);

    HistoryEntity findByWid(Integer wid);


}
