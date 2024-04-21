package com.huce.edu.services.Impls;

import com.huce.edu.entities.HistoryEntity;
import com.huce.edu.entities.UserEntity;
import com.huce.edu.entities.WordEntity;
import com.huce.edu.models.dto.WordQuestion;
import com.huce.edu.repositories.HistoryRepo;
import com.huce.edu.repositories.WordRepo;
import com.huce.edu.services.WordService;
import com.huce.edu.utils.ShuffleQuestionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {
    private final WordRepo wordRepo;
    private final HistoryRepo historyRepo;

    @Override
    public ArrayList<WordQuestion> getQuestionByTid(int tid) {
        ArrayList<WordQuestion> data = new ArrayList<>();
        List<WordEntity> wordsEntities = wordRepo.findByTid(tid);
        List<WordEntity> listRamdomWord;
        listRamdomWord = wordRepo.findByTid(tid + 1);
        if (listRamdomWord == null) {
            listRamdomWord = wordRepo.findByTid(tid - 1);
        }
        Collections.shuffle(wordsEntities);

        for (WordEntity currentWord : wordsEntities) {
            data.add(ShuffleQuestionUtil.shuffle(currentWord, listRamdomWord));
        }
        return data;
    }

    @Override
    public ArrayList<WordQuestion> getTest(UserEntity user) {
        ArrayList<WordQuestion> data = new ArrayList<>();

        ArrayList<HistoryEntity> listHistoryByUid = historyRepo.findByUid(user.getUid());
        Set<Integer> seenWid = new HashSet<>();

        for (HistoryEntity historyEntity : listHistoryByUid) {
            seenWid.add(historyEntity.getWid());
        }

        if (seenWid.size() <= 10) {
            return null;
        }

        int count = 0;
        for (int wid : seenWid) {
            if (count >= 20) break;
            count += 1;
            WordEntity currentWord = wordRepo.findByWid(wid);
            List<WordEntity> listRamdomWord = wordRepo.findByTid(currentWord.getTid() + 1);
            if (listRamdomWord == null) {
                listRamdomWord = wordRepo.findByTid(currentWord.getTid() - 1);
            }
            data.add(ShuffleQuestionUtil.shuffle(currentWord, listRamdomWord));
        }
        return data;
    }

    @Override
    public WordEntity add(WordEntity wordEntity) {
        wordEntity.setWid(0);
        wordRepo.save(wordEntity);
        return wordEntity;
    }

    @Override
    public WordEntity edit(WordEntity wordEntity) {
        wordRepo.save(wordEntity);
        return wordEntity;
    }

    @Override
    public WordEntity delete(Integer id) {
        WordEntity word = wordRepo.findByWid(id);
        wordRepo.delete(word);
        return word;
    }
}
