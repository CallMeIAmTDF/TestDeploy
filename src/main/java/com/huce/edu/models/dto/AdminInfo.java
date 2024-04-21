package com.huce.edu.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class AdminInfo {
    private int id;
    private String email;
    private String fullName;
    private String role;
}
