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
public class OrderDetailUpdate {

    private Long id;
    private Long orderId;
    private String paymentMethod;
    private int price;

    @Builder
    public OrderDetailUpdate(Long id, Long orderId, String paymentMethod, int price) {
        this.id = id;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.price = price;
    }

    public OrderDetail toEntity(OrderTable order) {
        return OrderDetail.builder()
                .price(price)
                .id(id)
                .orderTable(order)
                .paymentMethod(PaymentMethod.valueOf(paymentMethod))
                .build();
    }
}
