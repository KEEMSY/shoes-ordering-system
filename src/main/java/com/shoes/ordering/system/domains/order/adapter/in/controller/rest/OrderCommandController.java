package com.shoes.ordering.system.domains.order.adapter.in.controller.rest;

import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderCommand;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderResponse;
import com.shoes.ordering.system.domains.order.domain.application.ports.input.message.service.OrderCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/orders", produces = "application/vnd.API.v1+json")
public class OrderCommandController {

    private final OrderCommandService orderCommandService;

    public OrderCommandController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        log.info("Creating order for member: {} ", createOrderCommand.getMemberId());
        CreateOrderResponse createOrderResponse = orderCommandService.createOrder(createOrderCommand);
        log.info("Order created with tracking id: {}", createOrderResponse.getOrderTrackingId());
        return ResponseEntity.ok(createOrderResponse);
    }

    @PostMapping("/limited")
    public ResponseEntity<CreateOrderResponse> createLimitedOrder(@RequestBody CreateOrderCommand createOrderCommand) {
        log.info("Creating limited order for member: {} ", createOrderCommand.getMemberId());
        CreateOrderResponse createOrderResponse = orderCommandService.createLimitedOrder(createOrderCommand);
        log.info("Limited Order created with tracking id: {}", createOrderResponse.getOrderTrackingId());
        return ResponseEntity.ok(createOrderResponse);
    }
}
