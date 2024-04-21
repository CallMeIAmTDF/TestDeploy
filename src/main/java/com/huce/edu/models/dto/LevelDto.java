package com.huce.edu.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class LevelDto {
	int lid;
	String levelName;
	Integer numTopics;
	Integer numWords;
	Float process;
}
