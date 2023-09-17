package com.shoes.ordering.system.domains.order.domain.application;

import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderCommand;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderResponse;
import com.shoes.ordering.system.domains.order.domain.application.handler.CreateOrderCommandHandler;
import com.shoes.ordering.system.domains.order.domain.application.ports.input.message.service.OrderCommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCommandServiceImpl implements OrderCommandService {

    private final CreateOrderCommandHandler createOrderCommandHandler;

    public OrderCommandServiceImpl(CreateOrderCommandHandler createOrderCommandHandler) {
        this.createOrderCommandHandler = createOrderCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return createOrderCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public CreateOrderResponse createLimitedOrder(CreateOrderCommand createOrderCommand) {
        return createOrderCommandHandler.createLimitedOrder(createOrderCommand);
    }
}
