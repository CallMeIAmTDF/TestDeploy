package com.huce.edu.services.Impls;

import com.huce.edu.entities.LevelEntity;
import com.huce.edu.entities.TopicEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.models.dto.LevelDto;
import com.huce.edu.models.mapper.WordId;
import com.huce.edu.repositories.HistoryRepo;
import com.huce.edu.repositories.LevelRepo;
import com.huce.edu.repositories.TopicRepo;
import com.huce.edu.repositories.WordRepo;
import com.huce.edu.services.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LevelServiceImpl implements LevelService {
    private final WordRepo wordRepo;
    private final TopicRepo topicRepo;
    private final HistoryRepo historyRepo;
    private final LevelRepo levelRepo;

    @Override
    public ArrayList<LevelDto> getAll(UserEntity user) {
        ArrayList<LevelDto> listLevelDto = new ArrayList<>();
        List<LevelEntity> listLevelsEntities = levelRepo.findAll();
        int uid = 0;
        if (user != null)
            uid = user.getUid();

        for (LevelEntity levelEntity : listLevelsEntities) {
            List<TopicEntity> listTopicsByLid = topicRepo.findByLid(levelEntity.getLid());
            int numTopic = listTopicsByLid.size();
            List<Integer> words = new ArrayList<>(wordRepo.getByTidIn(listTopicsByLid.stream().map(TopicEntity::getTid).toList())).stream().map(WordId::getWid).toList();
            int numCorrects = (int) historyRepo.findByUid(uid).stream().filter(t -> t.getIscorrect() == 1 && words.contains(t.getWid())).count();
            listLevelDto.add(LevelDto.create(levelEntity.getLid(), levelEntity.getLevel(), numTopic, words.size(), numCorrects == 0 ? 0 : (float)  numCorrects/words.size()*100));
        }
        return listLevelDto;
    }

    @Override
    public LevelEntity add(String name) {
        LevelEntity newLevel = LevelEntity.create(0, name);
        levelRepo.save(newLevel);
        return newLevel;
    }

    @Override
    public LevelEntity edit(LevelEntity levelEntity) {
        levelRepo.save(levelEntity);
        return levelEntity;
    }

    @Override
    public LevelEntity delete(Integer id) {
        LevelEntity level = levelRepo.findByLid(id);
        levelRepo.delete(level);

        return level;
    }

}
