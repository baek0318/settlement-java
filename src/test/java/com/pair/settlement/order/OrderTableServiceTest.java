package com.pair.settlement.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderTableServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderDetailRepository orderDetailRepository;

    @InjectMocks
    OrderService orderService;

    @Test
    void 주문정보_가져오기에서_모든_Param이_null인경우() {
        Assertions.assertThatThrownBy(
                () -> orderService.findInfo(null, null, null, null)
        ).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 주문정보_가져오기에서_모든_Param이_길이가_0인경우() {
        Assertions.assertThatThrownBy(
                () -> orderService.findInfo("", "", "", "")
        ).isInstanceOf(RuntimeException.class);
    }
}
