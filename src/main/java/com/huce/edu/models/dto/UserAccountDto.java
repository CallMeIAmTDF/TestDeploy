package com.huce.edu.models.dto;

import com.huce.edu.shareds.Constants;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountDto {
    @NotNull
    private String name;
    @NotNull
    @Size(min = 8, max = 20, message = Constants.INVALID_PASSWORD_MIN_LENGTH)
    private String password;
    @NotNull
    @Email(message = Constants.INVALID_EMAIL)
    private String email;
}
