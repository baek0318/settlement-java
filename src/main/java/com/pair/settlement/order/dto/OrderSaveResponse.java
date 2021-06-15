package com.pair.settlement.order.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSaveResponse {

    private Long id;

    private List<Long> detailIds;

    public OrderSaveResponse(Long id, List<Long> detailIds) {
        this.id = id;
        this.detailIds = detailIds;
    }
}
