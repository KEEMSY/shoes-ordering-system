package com.shoes.ordering.system.domains.order.adapter.out.dataaccess.adapter;

import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.mapper.OrderDataAccessMapper;
import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.repository.OrderJpaRepository;
import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.repository.OrderQuerydslRepository;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.repository.OrderRepository;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class OrderPersistenceAdapter implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderQuerydslRepository orderQuerydslRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    public OrderPersistenceAdapter(OrderJpaRepository orderJpaRepository,
                                   OrderQuerydslRepository orderQuerydslRepository,
                                   OrderDataAccessMapper orderDataAccessMapper) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderQuerydslRepository = orderQuerydslRepository;
        this.orderDataAccessMapper = orderDataAccessMapper;
    }

    @Override
    public Order save(Order order) {
        return orderDataAccessMapper
                .orderEntityToOrder(orderJpaRepository.save(orderDataAccessMapper.orderToOrderEntity(order)));
    }

    public Optional<Order> findByTrackingId(UUID trackingId) {
        return orderQuerydslRepository.findByTrackingId(trackingId)
                .map(orderDataAccessMapper::orderEntityToOrder);
    }
}
