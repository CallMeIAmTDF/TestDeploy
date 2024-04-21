package com.huce.edu.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTopicByLevel {
	LevelDto levelDto;
	ArrayList<TopicByLevel> listTopics;
}
