package com.huce.edu.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicByLevel {
	private Integer tid;
	private String topic;
	private Integer lid;
	private Float process;
}
