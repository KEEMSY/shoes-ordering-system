package com.shoes.ordering.system.domains.order.domain.core;

import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCancelledEvent;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCreatedEvent;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService{


    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order) {
        return null;
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        return null;
    }

    @Override
    public void approveOrder(Order order) {

    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        return null;
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {

    }
}
