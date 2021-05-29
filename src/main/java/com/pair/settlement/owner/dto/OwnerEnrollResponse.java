package com.pair.settlement.owner.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerEnrollResponse {

    private Long id;

    public OwnerEnrollResponse(Long id) {
        this.id = id;
    }
}
