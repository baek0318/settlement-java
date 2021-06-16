package com.pair.settlement.order.dto;

import com.pair.settlement.order.OrderDetail;
import com.pair.settlement.order.OrderTable;
import com.pair.settlement.order.PaymentMethod;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetailUpdateRequest {

    private Long id;
    private Long orderId;
    private String paymentMethod;
    private int price;

    @Builder
    public OrderDetailUpdateRequest(Long id, Long orderId, String paymentMethod, int price) {
        this.id = id;
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.price = price;
    }

    public OrderDetailUpdateRequest(OrderDetail detail) {
        this.id = detail.getId();
        this.orderId = detail.getOrderTable().getId();
        this.paymentMethod = detail.getPaymentMethod().toString();
        this.price = detail.getPrice();
    }

    public OrderDetail toEntity() {
        OrderDetail detail = OrderDetail.builder()
                .price(price)
                .paymentMethod(PaymentMethod.valueOf(paymentMethod))
                .build();
        detail.setId(id);
        return detail;
    }
}
