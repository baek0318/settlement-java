package com.pair.order.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderWithDetailFind {

    private Long orderId;

    public OrderWithDetailFind(Long orderId) {
        this.orderId = orderId;
    }
}
