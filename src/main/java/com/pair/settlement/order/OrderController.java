package com.pair.settlement.order;

import com.pair.settlement.order.dto.*;
import com.querydsl.core.types.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
                        .paymentMethod(PaymentMethod.valueOf(it.getPaymentMethod()))
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

    @GetMapping("")
    public ResponseEntity<OrderInfoListResponse> getInfos(@RequestParam Map<String, String> param) {

        List<OrderTable> result = orderService.findInfo(
                param.get("owner-id"),
                param.get("order-id"),
                param.get("fromDateTime"),
                param.get("toDateTime")
        );

        return ResponseEntity.ok(new OrderInfoListResponse(result));
    }

    @GetMapping("/{order-id}/detail")
    public ResponseEntity<OrderInfoResponse> getInfoDetail(@PathVariable(name = "order-id") Long id) {
        return ResponseEntity.ok(orderService.findInfoWithDetail(id));
    }

    @PutMapping("")
    public ResponseEntity<OrderInfoResponse> updateOrder(@RequestBody OrderUpdateRequest updateRequest) {
        List<OrderDetail> details = updateRequest.getDetails().stream()
                .map(OrderDetailUpdateRequest::toEntity)
                .collect(Collectors.toList());
        OrderInfoResponse response = orderService.update(updateRequest.getOwnerId(), details, updateRequest.toEntity());

        return ResponseEntity.ok(response);
    }
}
