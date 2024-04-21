package com.huce.edu.models.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class HistoryDto {
	private Integer uid;
	private Integer wid;
	private Integer iscorrect;
}
