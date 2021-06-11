package com.pair.settlement.order;

import com.pair.settlement.order.dto.OrderDetailSaveRequest;
import com.pair.settlement.order.dto.OrderSaveResponse;
import com.pair.settlement.owner.Owner;
import com.pair.settlement.owner.OwnerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OwnerService ownerService;

    public OrderService(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, OwnerService ownerService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.ownerService = ownerService;
    }

    public OrderSaveResponse save(Long ownerId, List<OrderDetail> details, OrderTable order) {
        Owner owner = ownerService.findOwner(ownerId);
        order.setOwner(owner);
        OrderTable orderTable = orderRepository.save(order);
        List<Long> detailsId = new ArrayList<>();
        for(OrderDetail detail : details) {
            detail.setOrder(orderTable);
            detailsId.add(orderDetailRepository.save(detail).getId());
        }
        return new OrderSaveResponse(orderTable.getId(), detailsId);
    }
}
