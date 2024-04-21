package com.huce.edu.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}