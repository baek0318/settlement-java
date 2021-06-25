package com.pair.order;

import com.pair.order.dto.OrderFind;
import com.pair.order.dto.OrderWithDetailFind;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderTableServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    OrderDetailService orderDetailService;

    @InjectMocks
    OrderService orderService;

    @Test
    void 주문정보_가져오기에서_모든_Param이_null인경우() {
        Assertions.assertThatThrownBy(
                () -> orderService.findInfo(new OrderFind(null, null, null, null))
        ).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 주문정보_가져오기에서_모든_Param이_길이가_0인경우() {
        Assertions.assertThatThrownBy(
                () -> orderService.findInfo(new OrderFind("", "", "", ""))
        ).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 주문정보_가져올때_주문아이디값이_null인_경우() {
        Assertions.assertThatThrownBy(
                () -> orderService.findInfoWithDetail(null)
        ).isInstanceOf(RuntimeException.class);
    }

    @Test
    void 주문정보_가져올때_주문아이디값이_존재하지않는_경우() {
        when(orderRepository.findById(2L))
                .thenThrow(new IllegalArgumentException("일치하는 아이디값이 없습니다"));
        Assertions.assertThatThrownBy(
                () -> orderService.findInfoWithDetail(new OrderWithDetailFind(2L)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
