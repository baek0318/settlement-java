package com.pair.order.dto.response;

import com.pair.order.OrderStatus;
import com.pair.order.OrderTable;
import com.pair.order.dto.OrderDetailInfo;
import com.pair.order.dto.OrderInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public OrderInfoResponse(OrderInfo orderInfo) {
        this.ownerId = orderInfo.getOwnerId();
        this.orderId = orderInfo.getOrderId();
        this.totalPrice = orderInfo.getTotalPrice();
        this.status = orderInfo.getStatus();
        this.createdAt = orderInfo.getCreatedAt();
    }

    public OrderInfoResponse(OrderInfo orderInfo, List<OrderDetailInfo> detailInfos) {
        this.ownerId = orderInfo.getOwnerId();
        this.orderId = orderInfo.getOrderId();
        this.totalPrice = orderInfo.getTotalPrice();
        this.status = orderInfo.getStatus();
        this.createdAt = orderInfo.getCreatedAt();
        this.details = detailInfos.stream()
                .map(OrderDetailInfoResponse::new)
                .collect(Collectors.toList());
    }
}
