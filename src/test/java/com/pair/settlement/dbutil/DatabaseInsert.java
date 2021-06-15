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
    public Owner saveOwner(String name, String email, String phoneNumber) {
        Owner owner = Owner.builder()
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
        entityManager.persist(owner);
        return owner;
    }

    @Transactional
    public Long saveAccount(Long ownerId, String bank, String bankAccount, String accountHolder) {
        Owner owner = entityManager.find(Owner.class, ownerId);
        Account account = new Account(owner, bank, bankAccount, accountHolder);
        entityManager.persist(account);
        return account.getId();
    }

    @Transactional
    public OrderTable saveOrder(Owner owner, int totalPrice, OrderStatus status, LocalDateTime createdAt) {
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
    public OrderDetail saveOrderDetail(OrderTable order, PaymentMethod paymentMethod, int price) {
        OrderDetail orderDetail = OrderDetail.builder()
                .orderTable(order)
                .price(price)
                .paymentMethod(paymentMethod)
                .build();
        entityManager.persist(orderDetail);
        return orderDetail;
    }
}
