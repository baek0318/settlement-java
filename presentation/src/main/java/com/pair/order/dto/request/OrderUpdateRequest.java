package com.pair.order.dto.request;

import com.pair.order.OrderStatus;
import com.pair.order.OrderTable;
import com.pair.order.dto.OrderDetailUpdate;
import com.pair.order.dto.OrderUpdate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public OrderUpdate toOrderUpdate() {
        return OrderUpdate.builder()
                .createdAt(createdAt)
                .details(details.stream().map(it -> OrderDetailUpdate.builder()
                        .orderId(it.getOrderId())
                        .price(it.getPrice())
                        .paymentMethod(it.getPaymentMethod())
                        .id(it.getId())
                        .build()
                )
                        .collect(Collectors.toList())
                )
                .id(id)
                .ownerId(ownerId)
                .status(status)
                .totalPrice(totalPrice)
                .build();
    }
}
