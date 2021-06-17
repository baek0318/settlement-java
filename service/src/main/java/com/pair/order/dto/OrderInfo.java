package com.pair.order.dto;

import com.pair.order.OrderStatus;
import com.pair.order.OrderTable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderInfo {

    private Long ownerId;
    private Long orderId;
    private int totalPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderDetailInfo> details;

    public OrderInfo(OrderTable order) {
        this.orderId = order.getId();
        this.ownerId = order.getOwner().getId();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
    }
}
