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

        OrderSaveInfo response = orderService.save(saveRequest.toSaveDto());

        UriComponents uriComponents = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder.on(OrderController.class).save(saveRequest))
                .build();
        return ResponseEntity
                .created(uriComponents.toUri())
                .body(new OrderSaveResponse(response));
    }

    @GetMapping("")
    public ResponseEntity<OrderInfoListResponse> getInfos(@RequestParam Map<String, String> param) {

        List<OrderTable> result = orderService.findInfo(new OrderFind(param));

        return ResponseEntity.ok(new OrderInfoListResponse(result));
    }

    @GetMapping("/{order-id}/detail")
    public ResponseEntity<OrderInfoResponse> getInfoDetail(@PathVariable(name = "order-id") Long id) {
        OrderInfo result = orderService.findInfoWithDetail(new OrderWithDetailFind(id));
        OrderInfoResponse response = new OrderInfoResponse(result, result.getDetails());
        return ResponseEntity.ok(response);
    }

    @PutMapping("")
    public ResponseEntity<OrderInfoResponse> updateOrder(@RequestBody OrderUpdateRequest updateRequest) {
        OrderInfo result = orderService.update(updateRequest.toOrderUpdate());
        OrderInfoResponse response = new OrderInfoResponse(result, result.getDetails());
        return ResponseEntity.ok(response);
    }
}
