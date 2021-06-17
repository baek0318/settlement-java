package com.pair.order;


import java.util.List;

public interface OrderRepositoryCustom {

    List<OrderTable> findOrder(String ownerId, String orderId, String fromDateTime, String toDateTime);
}
