package com.pair.order.dto;

import com.pair.order.OrderDetail;
import com.pair.order.OrderStatus;
import com.pair.order.OrderTable;
import com.pair.owner.Owner;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSave {

    private Long ownerId;

    private int totalPrice;

    private String status;

    private LocalDateTime createdAt;

    private List<OrderDetailSave> details;

    @Builder
    public OrderSave(Long ownerId, int totalPrice, String status, LocalDateTime createdAt, List<OrderDetailSave> details) {
        this.ownerId = ownerId;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
        this.details = details;
    }

    public OrderTable toEntity(Owner owner) {
        return OrderTable.builder()
                .createdAt(createdAt)
                .owner(owner)
                .status(OrderStatus.valueOf(status))
                .totalPrice(totalPrice)
                .build();
    }
}
