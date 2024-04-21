package com.huce.edu.utils;

import com.huce.edu.entities.WordEntity;
import com.huce.edu.models.dto.WordQuestion;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShuffleQuestionUtil {
    public static WordQuestion shuffle(WordEntity currentWord, List<WordEntity> listRamdomWord){
        Random random = new Random();
        Collections.shuffle(listRamdomWord);
        int ran = random.nextInt(4);
        switch (ran) {
            case 0:
                return new WordQuestion(currentWord, currentWord.getWord(), listRamdomWord.get(1).getWord(), listRamdomWord.get(2).getWord(), listRamdomWord.get(0).getWord());
            case 1:
                return new WordQuestion(currentWord, listRamdomWord.get(1).getWord(), currentWord.getWord(), listRamdomWord.get(2).getWord(), listRamdomWord.get(0).getWord());
            case 2:
                return new WordQuestion(currentWord, listRamdomWord.get(1).getWord(),  listRamdomWord.get(2).getWord(), currentWord.getWord(), listRamdomWord.get(0).getWord());
            case 3:
                return new WordQuestion(currentWord, listRamdomWord.get(1).getWord(), listRamdomWord.get(2).getWord(), listRamdomWord.get(0).getWord(), currentWord.getWord());
        }
        return null;
    }
}
