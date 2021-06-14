package com.pair.settlement.order;

import com.pair.settlement.TestConfig;
import com.pair.settlement.owner.Owner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@DataJpaTest(showSql = true)
@Import(TestConfig.class)
public class OrderTableRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TestEntityManager em;

    Owner owner1;
    Owner owner2;
    OrderTable orderTable1;
    OrderTable orderTable2;
    OrderTable orderTable3;

    @BeforeEach
    void setUp() {
        owner1 = Owner.builder()
                .name("peach")
                .phoneNumber("010-2222-3333")
                .email("peach@kakao.com")
                .build();
        owner2 = Owner.builder()
                .name("berry")
                .phoneNumber("010-2222-4444")
                .email("berry@kakao.com")
                .build();
        orderTable1 = OrderTable.builder()
                .owner(owner1)
                .createdAt(LocalDateTime.of(2021,6,14,11,20))
                .status(OrderStatus.WAITING)
                .totalPrice(5000)
                .build();
        orderTable2 = OrderTable.builder()
                .owner(owner1)
                .createdAt(LocalDateTime.of(2021,6,14,11,10))
                .status(OrderStatus.WAITING)
                .totalPrice(6000)
                .build();
        orderTable3 = OrderTable.builder()
                .owner(owner2)
                .createdAt(LocalDateTime.of(2021,6,14,11,10))
                .status(OrderStatus.WAITING)
                .totalPrice(7000)
                .build();
        em.persist(owner1);
        em.persist(owner2);
        em.persist(orderTable1);
        em.persist(orderTable2);
        em.persist(orderTable3);
    }

    @Test
    void 업주_아이디로_데이터_가져오기_테스트() {
        //given

        //when
        List<OrderTable> result = orderRepository.findOrder(Long.toString(owner1.getId()), null, "", "");

        //then
        Collections.sort(result, Comparator.comparingLong(OrderTable::getId));
        List<OrderTable> expected = Arrays.asList(orderTable1, orderTable2);
        orderTableAssert(result, expected);
    }

    @Test
    void 주문_아이디로_데이터_가져오기_테스트() {

        //when
        List<OrderTable> result = orderRepository.findOrder(null, Long.toString(orderTable1.getId()), "", "");

        //then
        List<OrderTable> expected = Arrays.asList(orderTable1);
        orderTableAssert(result, expected);
    }

    @Test
    void 날짜_범위로_데이터_가져오기_테스트() {
        //when
        List<OrderTable> result = orderRepository.findOrder(null, null, "2021-06-14T11:10", "2021-06-14T11:20");

        //then
        Collections.sort(result, Comparator.comparingLong(OrderTable::getId));
        List<OrderTable> expected = Arrays.asList(orderTable1, orderTable2, orderTable3);
        orderTableAssert(result, expected);
    }

    @Test
    void 시작_날짜만_존재하는경우_데이터_가져오기_테스트() {
        //when
        List<OrderTable> result = orderRepository.findOrder(null, null, "2021-06-14T11:10", null);

        //then
        Collections.sort(result, Comparator.comparingLong(OrderTable::getId));
        List<OrderTable> expected = Arrays.asList(orderTable1, orderTable2, orderTable3);
        orderTableAssert(result, expected);
    }

    @Test
    void 종료_날짜만_존재하는경우_데이터_가져오기_테스트() {
        //when
        List<OrderTable> result = orderRepository.findOrder(null, null, "", "2021-06-14T11:10");

        //then
        Collections.sort(result, Comparator.comparingLong(OrderTable::getId));
        List<OrderTable> expected = Arrays.asList(orderTable2, orderTable3);
        orderTableAssert(result, expected);
    }

    private void orderTableAssert(List<OrderTable> result, List<OrderTable> expected) {
        Assertions.assertThat(result.size()).isEqualTo(expected.size());
        for (int i = 0; i < result.size(); i++) {
            Assertions.assertThat(result.get(i).getId()).isEqualTo(expected.get(i).getId());
            Assertions.assertThat(result.get(i).getStatus()).isEqualTo(expected.get(i).getStatus());
            Assertions.assertThat(result.get(i).getCreatedAt()).isEqualTo(expected.get(i).getCreatedAt());
            Assertions.assertThat(result.get(i).getTotalPrice()).isEqualTo(expected.get(i).getTotalPrice());
            Assertions.assertThat(result.get(i).getOwner().getId()).isEqualTo(expected.get(i).getOwner().getId());
        }
    }
}
