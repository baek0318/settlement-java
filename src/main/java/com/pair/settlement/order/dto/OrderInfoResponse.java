package com.pair.settlement.order.dto;

import com.pair.settlement.order.OrderStatus;
import com.pair.settlement.order.OrderTable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderInfoResponse {

    private Long ownerId;
    private Long orderId;
    private int totalPrice;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderDetailInfoResponse> details;

    public OrderInfoResponse(OrderTable order) {
        this.orderId = order.getId();
        this.ownerId = order.getOwner().getId();
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
    }
}
