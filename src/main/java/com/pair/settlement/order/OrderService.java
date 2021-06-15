package com.pair.settlement.order;

import com.pair.settlement.order.dto.OrderDetailSaveRequest;
import com.pair.settlement.order.dto.OrderInfoResponse;
import com.pair.settlement.order.dto.OrderSaveResponse;
import com.pair.settlement.owner.Owner;
import com.pair.settlement.owner.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;
    private final OwnerService ownerService;

    public OrderService(OrderRepository orderRepository, OrderDetailService orderDetailService, OwnerService ownerService) {
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
        this.ownerService = ownerService;
    }

    public OrderSaveResponse save(Long ownerId, List<OrderDetail> details, OrderTable order) {
        Owner owner = ownerService.findOwner(ownerId);
        order.setOwner(owner);
        OrderTable orderTable = orderRepository.save(order);
        return new OrderSaveResponse(
                orderTable.getId(),
                orderDetailService.saveDetails(details, orderTable)
        );
    }

    public List<OrderTable> findInfo(String ownerId, String orderId, String fromDateTime, String toDateTime) {
        checkAllParamNull(ownerId, orderId, fromDateTime, toDateTime);
        return orderRepository.findOrder(ownerId, orderId, fromDateTime, toDateTime);
    }

    private void checkAllParamNull(String ownerId, String orderId, String fromDateTime, String toDateTime) {
        if((ownerId == null || ownerId.isEmpty())
                && (orderId == null || orderId.isEmpty())
                && (fromDateTime == null || fromDateTime.isEmpty())
                && (toDateTime == null || toDateTime.isEmpty())){
            throw new RuntimeException("모든 param이 null이여서는 안됩니다");
        }
    }

    public ResponseEntity<OrderInfoResponse> findInfoWithDetail(Long orderId) {

        return null;
    }
}
