package com.shoes.ordering.system.domains.order.adapter.out.dataaccess.repository;

import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.entity.OrderEntity;

import java.util.Optional;
import java.util.UUID;

public interface OrderQuerydslRepository {
    Optional<OrderEntity> findByTrackingId(UUID trackId);
}
