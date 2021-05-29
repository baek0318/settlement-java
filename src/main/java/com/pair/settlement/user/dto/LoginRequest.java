package com.pair.settlement.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    private String email;

    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
