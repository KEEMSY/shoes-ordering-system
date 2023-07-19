package com.shoes.ordering.system.domains.product.domain.application.ports.output.message.publisher;

import com.shoes.ordering.system.domains.common.event.publisher.DomainEventPublisher;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;

public interface ProductCreatedRequestMessagePublisher extends DomainEventPublisher<ProductCreatedEvent> {
}
