package com.pair.settlement.order.detail;

import com.pair.settlement.order.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceTest {

    @Mock
    OrderDetailRepository orderDetailRepository;

    @InjectMocks
    OrderDetailService orderDetailService;

    @Test
    void 주문_인스턴스가_비어있는_경우() {
        OrderDetail orderDetail = OrderDetail.builder()
                .paymentMethod(PaymentMethod.CARD)
                .price(2000)
                .build();
        List<OrderDetail> list = Arrays.asList(orderDetail);
        Assertions.assertThatThrownBy(
                () -> orderDetailService.saveDetails(list, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 총_주문_금액이_일치하지_않는경우() {
        OrderTable order = OrderTable.builder()
                .totalPrice(6000)
                .build();
        OrderDetail orderDetail = OrderDetail.builder()
                .paymentMethod(PaymentMethod.CARD)
                .price(2000)
                .orderTable(order)
                .build();
        OrderDetail orderDetail2 = OrderDetail.builder()
                .paymentMethod(PaymentMethod.CASH)
                .price(3000)
                .orderTable(order)
                .build();
        List<OrderDetail> list = Arrays.asList(orderDetail, orderDetail2);
        Assertions.assertThatThrownBy(() -> orderDetailService.saveDetails(list, order))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void Order에_제대로_추가되는지_확인() {
        OrderTable order = OrderTable.builder()
                .totalPrice(5000)
                .build();
        OrderDetail orderDetail = OrderDetail.builder()
                .paymentMethod(PaymentMethod.CARD)
                .price(2000)
                .orderTable(order)
                .build();
        OrderDetail orderDetail2 = OrderDetail.builder()
                .paymentMethod(PaymentMethod.CASH)
                .price(3000)
                .orderTable(order)
                .build();
        List<OrderDetail> list = Arrays.asList(orderDetail, orderDetail2);

        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);
        when(orderDetailRepository.save(orderDetail2)).thenReturn(orderDetail2);
        orderDetailService.saveDetails(list, order);

        Assertions.assertThat(order.getOrderDetails().size()).isEqualTo(list.size());
    }
}
