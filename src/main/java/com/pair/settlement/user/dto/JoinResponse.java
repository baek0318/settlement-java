package com.pair.settlement.user.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinResponse {

    private Long id;

    public JoinResponse(Long id) {
        this.id = id;
    }
}
