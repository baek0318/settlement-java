package com.pair.settlement.order.dto;

import com.pair.settlement.order.OrderDetail;
import com.pair.settlement.order.PaymentMethod;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetailInfoResponse {

    private Long orderDetailId;

    private PaymentMethod paymentMethod;

    private int price;

    public OrderDetailInfoResponse(OrderDetail orderDetail) {
        this.orderDetailId = orderDetail.getId();
        this.paymentMethod = orderDetail.getPaymentMethod();
        this.price = orderDetail.getPrice();
    }
}
