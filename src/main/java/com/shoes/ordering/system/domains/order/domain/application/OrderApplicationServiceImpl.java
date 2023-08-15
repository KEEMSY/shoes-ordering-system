package com.shoes.ordering.system.domains.order.domain.application;

import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderCommand;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderResponse;
import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderQuery;
import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderResponse;
import com.shoes.ordering.system.domains.order.domain.application.handler.CreateOrderCommandHandler;
import com.shoes.ordering.system.domains.order.domain.application.ports.input.message.service.OrderApplicationService;

public class OrderApplicationServiceImpl implements OrderApplicationService {

    private final CreateOrderCommandHandler createOrderCommandHandler;

    public OrderApplicationServiceImpl(CreateOrderCommandHandler createOrderCommandHandler) {
        this.createOrderCommandHandler = createOrderCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return createOrderCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return null;
    }
}
