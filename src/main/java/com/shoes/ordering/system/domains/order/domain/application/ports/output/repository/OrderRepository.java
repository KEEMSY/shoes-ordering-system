package com.shoes.ordering.system.domains.order.domain.application.ports.output.repository;

import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findByTrackingId(TrackingId trackingId);
}
