package com.pair.settlement.order.dto;

import com.pair.settlement.order.OrderTable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderInfoListResponse {

    private List<OrderInfoResponse> orderInfoList;

    public OrderInfoListResponse(List<OrderTable> orderList) {
        this.orderInfoList = orderList.stream()
                .map(OrderInfoResponse::new)
                .collect(Collectors.toList());
    }
}
