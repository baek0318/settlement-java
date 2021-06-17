package com.pair.order;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDateTime;
import java.util.List;

import static com.pair.owner.QOwner.owner;
import static com.pair.order.QOrderTable.orderTable;

public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    public OrderRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<OrderTable> findOrder(String ownerId, String orderId, String fromDateTime, String toDateTime) {
        return jpaQueryFactory.selectFrom(orderTable)
                .where(
                        getId(ownerId),
                        getOrderId(orderId),
                        getCreatedAt(fromDateTime, toDateTime)
                )
                .fetch();
    }

    private BooleanExpression getId(String id) {
        if(id == null || id.isEmpty()) {
            return null;
        }
        return owner.id.eq(Long.parseLong(id));
    }

    private BooleanExpression getOrderId(String orderId) {
        if(orderId == null || orderId.isEmpty()) {
            return null;
        }
        return orderTable.id.eq(Long.parseLong(orderId));
    }

    private BooleanExpression getCreatedAt(String fromDateTime, String toDateTime) {
        if((fromDateTime == null || fromDateTime.isEmpty()) && (toDateTime == null || toDateTime.isEmpty())) {
            return null;
        }
        if(fromDateTime == null || fromDateTime.isEmpty()) {
            return orderTable.createdAt.loe(LocalDateTime.parse(toDateTime));
        }
        if(toDateTime == null || toDateTime.isEmpty()) {
            return orderTable.createdAt.goe(LocalDateTime.parse(fromDateTime));
        }
        return orderTable.createdAt.between(LocalDateTime.parse(fromDateTime), LocalDateTime.parse(toDateTime));
    }
}
