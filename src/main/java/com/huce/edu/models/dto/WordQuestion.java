package com.huce.edu.models.dto;

import com.huce.edu.entities.WordEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordQuestion {
	WordEntity words;
	String answerA;
	String answerB;
	String answerC;
	String answerD;
}
