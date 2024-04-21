package com.huce.edu.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataMailDto {
    private String to;
    private String subject;
    private String content;
    private Map<String, Object> props;
}