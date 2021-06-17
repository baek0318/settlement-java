package com.pair.order.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSaveInfo {

    private Long id;

    private List<Long> detailIds;

    public OrderSaveInfo(Long id, List<Long> detailIds) {
        this.id = id;
        this.detailIds = detailIds;
    }
}
