package com.pair.order.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetailSaveRequest {

    private String paymentMethod;

    private int price;

    @Builder
    public OrderDetailSaveRequest(String paymentMethod, int price) {
        this.paymentMethod = paymentMethod;
        this.price = price;
    }
}
