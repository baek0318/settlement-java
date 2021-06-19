package com.pair.order.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderFind {

    private String ownerId;
    private String orderId;
    private String fromDateTime;
    private String toDateTime;

    public OrderFind(String ownerId, String orderId, String fromDateTime, String toDateTime) {
        this.ownerId = ownerId;
        this.orderId = orderId;
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
    }

    public OrderFind(Map<String, String> params) {
        this.ownerId = params.get("com.pair.owner-id");
        this.orderId = params.get("com.pair.order-id");
        this.fromDateTime = params.get("fromDateTime");
        this.toDateTime = params.get("toDateTime");
    }
}
