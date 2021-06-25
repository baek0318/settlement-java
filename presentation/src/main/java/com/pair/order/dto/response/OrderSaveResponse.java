package com.pair.order.dto.response;

import com.pair.order.dto.OrderSaveInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderSaveResponse {

    private Long id;

    private List<Long> detailIds;

    public OrderSaveResponse(Long id, List<Long> detailIds) {
        this.id = id;
        this.detailIds = detailIds;
    }

    public OrderSaveResponse(OrderSaveInfo saveInfo) {
        this.id = saveInfo.getId();
        this.detailIds = saveInfo.getDetailIds();
    }
}
