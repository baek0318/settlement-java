package com.pair.settlement.dbutil;

import com.pair.settlement.order.OrderDetail;
import com.pair.settlement.order.OrderStatus;
import com.pair.settlement.order.OrderTable;
import com.pair.settlement.order.PaymentMethod;
import com.pair.settlement.owner.Account;
import com.pair.settlement.owner.Owner;
import com.pair.settlement.user.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Component
public class DatabaseInsert {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Long saveUser(User user) {
        entityManager.persist(user);
        return user.getId();
    }

    @Transactional
    public Long saveOwner(Owner owner) {
        entityManager.persist(owner);
        return owner.getId();
    }

    @Transactional
    public Long saveAccount(Long ownerId, String bank, String bankAccount, String accountHolder) {
        Owner owner = entityManager.find(Owner.class, ownerId);
        Account account = new Account(owner, bank, bankAccount, accountHolder);
        entityManager.persist(account);
        return account.getId();
    }

    @Transactional
    public OrderTable saveOrder(Long ownerId, int totalPrice, OrderStatus status, LocalDateTime createdAt) {
        Owner owner = entityManager.find(Owner.class, ownerId);
        OrderTable order = OrderTable.builder()
                .owner(owner)
                .totalPrice(totalPrice)
                .status(status)
                .createdAt(createdAt)
                .build();
        entityManager.persist(order);
        return order;
    }

    @Transactional
    public OrderDetail saveOrderDetail(Long orderId, PaymentMethod paymentMethod, int price) {
        OrderTable order = entityManager.find(OrderTable.class, orderId);
        OrderDetail orderDetail = OrderDetail.builder()
                .orderTable(order)
                .price(price)
                .paymentMethod(paymentMethod)
                .build();
        entityManager.persist(orderDetail);
        return orderDetail;
    }
}
