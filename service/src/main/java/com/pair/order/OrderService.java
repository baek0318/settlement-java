package com.pair.order;

import com.pair.order.dto.*;
import com.pair.owner.Owner;
import com.pair.owner.OwnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public OrderSaveInfo save(OrderSave orderSave) {
        Owner owner = ownerService.findOwner(orderSave.getOwnerId());
        OrderTable order = orderSave.toEntity(owner);
        OrderTable saved = orderRepository.save(order);
        return new OrderSaveInfo(
                saved.getId(),
                orderDetailService.saveDetails(
                        orderSave.getDetails().stream()
                                .map(it -> it.toEntity(saved))
                                .collect(Collectors.toList()),
                        saved)
        );
    }

    @Transactional
    public List<OrderTable> findInfo(OrderFind orderFind) {
        checkAllParamNull(orderFind);
        return orderRepository.findOrder(
                orderFind.getOwnerId(),
                orderFind.getOrderId(),
                orderFind.getFromDateTime(),
                orderFind.getToDateTime());
    }

    private void checkAllParamNull(OrderFind orderFind) {
        String ownerId = orderFind.getOwnerId();
        String orderId = orderFind.getOrderId();
        String fromDateTime = orderFind.getFromDateTime();
        String toDateTime = orderFind.getToDateTime();
        if((ownerId == null || ownerId.isEmpty())
                && (orderId == null || orderId.isEmpty())
                && (fromDateTime == null || fromDateTime.isEmpty())
                && (toDateTime == null || toDateTime.isEmpty())){
            throw new RuntimeException("모든 param이 null이여서는 안됩니다");
        }
    }

    @Transactional
    public OrderInfo findInfoWithDetail(OrderWithDetailFind orderWithDetailFind) {
        checkIdNull(orderWithDetailFind.getOrderId());
        OrderTable found = orderRepository.findById(orderWithDetailFind.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("일치하는 아이디가 없습니다"));
        OrderInfo response = new OrderInfo(found);
        response.setDetails(
                found.getOrderDetails().stream()
                        .map(OrderDetailInfo::new)
                        .collect(Collectors.toList()));
        return response;
    }

    private void checkIdNull(Long orderId) {
        if(orderId == null) {
            throw new RuntimeException("아이디값이 null이여서는 안됩니다");
        }
    }

    @Transactional
    public OrderInfo update(Long ownerId, List<OrderDetail> details, OrderTable order) {
        Owner owner = ownerService.findOwner(ownerId);
        order.setOwner(owner);
        OrderTable orderTable = orderRepository.save(order);
        OrderInfo response = new OrderInfo(orderTable);
        response.setDetails(orderDetailService.update(details, orderTable));
        return response;
    }
}
