package com.pair.order.dto.request;

import com.pair.order.OrderStatus;
import com.pair.order.OrderTable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderUpdateRequest {

    private Long id;
    private Long ownerId;
    private int totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderDetailUpdateRequest> details;

    @Builder
    public OrderUpdateRequest(Long id, Long ownerId, int totalPrice, String status, LocalDateTime createdAt, List<OrderDetailUpdateRequest> details) {
        this.id = id;
        this.ownerId = ownerId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.details = details;
    }

    public OrderTable toEntity() {
        OrderTable order = OrderTable.builder()
                .totalPrice(this.totalPrice)
                .status(OrderStatus.valueOf(this.status))
                .createdAt(this.createdAt)
                .build();
        order.setId(id);
        return order;
    }
}
