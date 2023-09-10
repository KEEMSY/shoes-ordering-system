package com.shoes.ordering.system.domains.order.domain.core.event;

import com.shoes.ordering.system.domains.common.event.DomainEvent;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;

    public OrderEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
