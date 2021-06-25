package com.pair.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderTable, Long>, OrderRepositoryCustom {
}
