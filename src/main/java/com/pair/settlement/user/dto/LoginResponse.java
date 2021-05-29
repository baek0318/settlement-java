package com.pair.settlement.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponse {

    private Long id;

    public LoginResponse(Long id) {
        this.id = id;
    }
}
