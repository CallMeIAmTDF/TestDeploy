package com.huce.edu.models.dto;

import com.huce.edu.shareds.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordDto {
    @NotNull
    @Size(min = 8, max = 20, message = Constants.INVALID_PASSWORD_MIN_LENGTH)
    private String password;
    @NotNull
    @Email(message = Constants.INVALID_EMAIL)
    private String email;
    @NotNull
    private String otp;
}