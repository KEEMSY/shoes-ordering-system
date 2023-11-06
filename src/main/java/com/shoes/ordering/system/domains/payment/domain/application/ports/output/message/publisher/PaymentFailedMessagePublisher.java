package com.shoes.ordering.system.domains.payment.domain.application.ports.output.message.publisher;

import com.shoes.ordering.system.domains.common.event.publisher.DomainEventPublisher;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentFailedEvent;
import org.springframework.stereotype.Component;

@Component
public interface PaymentFailedMessagePublisher extends DomainEventPublisher<PaymentFailedEvent> {
}
