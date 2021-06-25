package com.pair.order.dto;

import com.pair.order.OrderDetail;
import com.pair.order.PaymentMethod;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetailInfo {

    private Long id;

    private PaymentMethod paymentMethod;

    private int price;

    public OrderDetailInfo(OrderDetail orderDetail) {
        this.id = orderDetail.getId();
        this.paymentMethod = orderDetail.getPaymentMethod();
        this.price = orderDetail.getPrice();
    }
}
