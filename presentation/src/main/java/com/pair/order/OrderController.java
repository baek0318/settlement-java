package com.pair.order;

import com.pair.order.dto.*;
import com.pair.order.dto.request.OrderDetailUpdateRequest;
import com.pair.order.dto.request.OrderSaveRequest;
import com.pair.order.dto.request.OrderUpdateRequest;
import com.pair.order.dto.response.OrderInfoListResponse;
import com.pair.order.dto.response.OrderInfoResponse;
import com.pair.order.dto.response.OrderSaveResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

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
        OrderSaveInfo response = orderService.save(saveRequest.getOwnerId(), details, saveRequest.toEntity());

        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder.on(OrderController.class).save(saveRequest))
                .build();
        return ResponseEntity
                .created(uriComponents.toUri())
                .body(new OrderSaveResponse(response));
    }

    @GetMapping("")
    public ResponseEntity<OrderInfoListResponse> getInfos(@RequestParam Map<String, String> param) {

        List<OrderTable> result = orderService.findInfo(
                param.get("com.pair.owner-id"),
                param.get("com.pair.order-id"),
                param.get("fromDateTime"),
                param.get("toDateTime")
        );

        return ResponseEntity.ok(new OrderInfoListResponse(result));
    }

    @GetMapping("/{order-id}/detail")
    public ResponseEntity<OrderInfoResponse> getInfoDetail(@PathVariable(name = "order-id") Long id) {
        OrderInfo result = orderService.findInfoWithDetail(id);
        OrderInfoResponse response = new OrderInfoResponse(result, result.getDetails());
        return ResponseEntity.ok(response);
    }

    @PutMapping("")
    public ResponseEntity<OrderInfoResponse> updateOrder(@RequestBody OrderUpdateRequest updateRequest) {
        List<OrderDetail> details = updateRequest.getDetails().stream()
                .map(OrderDetailUpdateRequest::toEntity)
                .collect(Collectors.toList());
        OrderInfo result = orderService.update(updateRequest.getOwnerId(), details, updateRequest.toEntity());
        OrderInfoResponse response = new OrderInfoResponse(result, result.getDetails());
        return ResponseEntity.ok(response);
    }
}
