package com.huce.edu.models.response;

import com.huce.edu.models.dto.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class LoginResponse {
    private UserInfo userInfo;
    private TokenResponse tokens;
}