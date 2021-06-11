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
public class OrderSaveRequest {

    private Long ownerId;

    private int totalPrice;

    private String status;

    private LocalDateTime createdAt;

    private List<OrderDetailSaveRequest> details;

    @Builder
    public OrderSaveRequest(Long ownerId, int totalPrice, String status, LocalDateTime createdAt, List<OrderDetailSaveRequest> details) {
        this.ownerId = ownerId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.details = details;
    }

    public OrderTable toEntity() {
        return OrderTable.builder()
                .totalPrice(this.totalPrice)
                .status(OrderStatus.valueOf(this.status))
                .createdAt(this.createdAt)
                .build();
    }
}
