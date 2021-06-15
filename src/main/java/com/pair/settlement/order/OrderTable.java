package com.pair.settlement.order;

import com.pair.settlement.owner.Owner;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class OrderTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private Owner owner;

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "orderTable", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @Builder
    public OrderTable(Owner owner, int totalPrice, OrderStatus status, LocalDateTime createdAt) {
        this.owner = owner;
        this.totalPrice = totalPrice;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        orderDetails.add(orderDetail);
    }
}
