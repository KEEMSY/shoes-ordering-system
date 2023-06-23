package com.shoes.ordering.system.domains.common.event.publisher;

import com.shoes.ordering.system.domains.common.event.DomainEvent;

public interface DomainEventPublisher <T extends DomainEvent> {

    void publish(T domainEvent);
}
