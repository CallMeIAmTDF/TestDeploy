package com.huce.edu.models.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class OrderDto {
	private String phone;
	private String address;
	private Integer pid;
	private Integer quantity;
}
