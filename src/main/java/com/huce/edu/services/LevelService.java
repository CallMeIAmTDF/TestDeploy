package com.huce.edu.services;

import com.huce.edu.entities.LevelEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.models.dto.LevelDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface LevelService {
    ArrayList<LevelDto> getAll(UserEntity user);

    LevelEntity add(String name);

    LevelEntity edit(LevelEntity levelEntity);

    LevelEntity delete(Integer id);
}



