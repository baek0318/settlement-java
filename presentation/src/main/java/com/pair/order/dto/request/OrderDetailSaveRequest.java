package com.pair.order.dto.request;

import com.pair.order.dto.OrderDetailSave;
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

    public OrderDetailSave toSaveDto() {
        return OrderDetailSave.builder()
                .paymentMethod(paymentMethod)
                .price(price)
                .build();
    }
}
