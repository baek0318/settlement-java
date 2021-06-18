package com.pair.order.dto.request;

import com.pair.order.OrderStatus;
import com.pair.order.OrderTable;
import com.pair.order.dto.OrderSave;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public OrderSave toSaveDto() {
        return OrderSave.builder()
                .createdAt(createdAt)
                .details(details.stream()
                        .map(OrderDetailSaveRequest::toSaveDto)
                        .collect(Collectors.toList()))
                .ownerId(ownerId)
                .status(status)
                .totalPrice(totalPrice)
                .build();
    }
}
