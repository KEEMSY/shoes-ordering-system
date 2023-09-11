package com.shoes.ordering.system.domains.order.domain.core.event;

import com.shoes.ordering.system.domains.order.domain.core.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent{
    public OrderPaidEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
