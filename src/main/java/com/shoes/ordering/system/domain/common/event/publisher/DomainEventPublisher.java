package com.shoes.ordering.system.domain.common.event.publisher;

import com.shoes.ordering.system.domain.common.event.DomainEvent;

public interface DomainEventPublisher <T extends DomainEvent> {

    void publish(T domainEvent);
}
