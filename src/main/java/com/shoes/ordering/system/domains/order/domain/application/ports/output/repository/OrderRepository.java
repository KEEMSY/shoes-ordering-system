package com.shoes.ordering.system.domains.order.domain.application.ports.output.repository;

import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByTrackingId(UUID trackingId);
}
