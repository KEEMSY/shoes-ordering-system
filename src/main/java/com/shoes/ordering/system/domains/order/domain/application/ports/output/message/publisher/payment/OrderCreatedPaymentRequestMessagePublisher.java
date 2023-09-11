package com.shoes.ordering.system.domains.order.domain.application.ports.output.message.publisher.payment;

import com.shoes.ordering.system.domains.common.event.publisher.DomainEventPublisher;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
