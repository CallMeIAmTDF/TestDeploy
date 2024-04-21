package com.huce.edu.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class ProductDto {
	private String name;
	private Double price;
	private String image;
	private Integer remain;
}
