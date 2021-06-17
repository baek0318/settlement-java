package com.pair.owner.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountEnrollResponse {
    private Long id;

    public AccountEnrollResponse(Long id) {
        this.id = id;
    }
}
