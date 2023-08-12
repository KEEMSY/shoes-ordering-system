package com.shoes.ordering.system.domains.order.domain.core;

import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCancelledEvent;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCreatedEvent;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
