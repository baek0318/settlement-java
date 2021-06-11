package com.pair.settlement.order;

import com.pair.settlement.order.dto.OrderSaveRequest;
import com.pair.settlement.order.dto.OrderSaveResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("")
    public ResponseEntity<OrderSaveResponse> save(@RequestBody OrderSaveRequest saveRequest) {

        List<OrderDetail> details = saveRequest.getDetails().stream()
                .map(it -> OrderDetail.builder()
                        .paymentMethod(it.getPaymentMethod())
                        .price(it.getPrice())
                        .build())
                .collect(Collectors.toList());
        OrderSaveResponse response = orderService.save(saveRequest.getOwnerId(), details, saveRequest.toEntity());

        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder.on(OrderController.class).save(saveRequest))
                .build();
        return ResponseEntity
                .created(uriComponents.toUri())
                .body(response);
    }
}
