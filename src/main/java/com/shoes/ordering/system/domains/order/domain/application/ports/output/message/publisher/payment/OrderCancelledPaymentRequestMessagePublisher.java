package com.shoes.ordering.system.domains.order.domain.application.ports.output.message.publisher.payment;

import com.shoes.ordering.system.domains.common.event.publisher.DomainEventPublisher;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}