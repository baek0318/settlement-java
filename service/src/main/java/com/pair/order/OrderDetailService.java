package com.pair.order;

import com.pair.order.OrderDetail;
import com.pair.order.OrderDetailRepository;
import com.pair.order.dto.OrderDetailInfo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Transactional
    public List<Long> saveDetails(List<OrderDetail> orderDetails, OrderTable order) {
        checkOrderIsNull(order);
        isSameTotalPrice(orderDetails.stream()
                .mapToInt(OrderDetail::getPrice)
                .reduce(0, Integer::sum),
                order.getTotalPrice());
        List<Long> result = new ArrayList<>();
        for(OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(order);
            OrderDetail saved = orderDetailRepository.save(orderDetail);
            order.setOrderDetail(saved);
            result.add(saved.getId());
        }
        return result;
    }

    private void checkOrderIsNull(OrderTable order) {
        if(order == null) {
            throw new IllegalArgumentException("Order가 null이여서는 안됩니다");
        }
    }

    private void isSameTotalPrice(int detailPrice, int totalPrice) {
        if(detailPrice != totalPrice) {
            throw new RuntimeException("detail Price와 totalPrice는 일치해야 합니다");
        }
    }

    @Transactional
    public List<OrderDetailInfo> update(List<OrderDetail> details, OrderTable orderTable) {
        checkOrderIsNull(orderTable);
        isSameTotalPrice(
                details.stream().mapToInt(OrderDetail::getPrice).reduce(0, Integer::sum),
                orderTable.getTotalPrice()
        );
        List<OrderDetailInfo> response = new ArrayList<>();
        for(OrderDetail detail : details) {
            detail.setOrder(orderTable);
            OrderDetail updated = orderDetailRepository.save(detail);
            response.add(new OrderDetailInfo(updated));
        }
        return response;
    }
}
