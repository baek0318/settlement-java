package com.pair.order.dto.response;

import com.pair.order.OrderDetail;
import com.pair.order.PaymentMethod;
import com.pair.order.dto.OrderDetailInfo;
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

    public OrderDetailInfoResponse(OrderDetailInfo detailInfo) {
        this.orderDetailId = detailInfo.getId();
        this.paymentMethod = detailInfo.getPaymentMethod();
        this.price = detailInfo.getPrice();
    }
}
