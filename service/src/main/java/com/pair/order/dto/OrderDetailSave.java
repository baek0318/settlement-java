package com.pair.order.dto;

import com.pair.order.OrderDetail;
import com.pair.order.OrderTable;
import com.pair.order.PaymentMethod;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetailSave {

    private String paymentMethod;

    private int price;

    @Builder
    public OrderDetailSave(String paymentMethod, int price) {
        this.paymentMethod = paymentMethod;
        this.price = price;
    }

    public OrderDetail toEntity(OrderTable order) {
        return OrderDetail.builder()
                .orderTable(order)
                .paymentMethod(PaymentMethod.valueOf(paymentMethod))
                .price(price)
                .build();
    }
}
