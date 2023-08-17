package com.shoes.ordering.system.domains.order.domain.application.handler;

import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderQuery;
import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderResponse;
import com.shoes.ordering.system.domains.order.domain.application.mapper.OrderDataMapper;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.repository.OrderRepository;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.exception.OrderNotFoundException;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.TrackingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class TrackOrderQueryHandler {

    private final OrderDataMapper orderDataMapper;

    private final OrderRepository orderRepository;

    public TrackOrderQueryHandler(OrderDataMapper orderDataMapper,
                                  OrderRepository orderRepository) {
        this.orderDataMapper = orderDataMapper;
        this.orderRepository = orderRepository;
    }

    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> orderResult =
                orderRepository.findByTrackingId(new TrackingId(trackOrderQuery.getOrderTrackingId()));

        if (orderResult.isEmpty()) {
            log.warn("Could not find order with tracking id: {}", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Could not find order with tracking id: " +
                    trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(orderResult.get());
    }
}
