package com.shoes.ordering.system.domains.order.domain.application.ports.input.message.service;

import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderCommand;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderResponse;

import javax.validation.Valid;

public interface OrderCommandService {

    CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);

}
