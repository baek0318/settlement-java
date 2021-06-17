package com.pair.user.dto.response;

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
