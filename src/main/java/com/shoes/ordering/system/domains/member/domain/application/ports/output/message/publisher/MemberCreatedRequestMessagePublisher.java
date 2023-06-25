package com.shoes.ordering.system.domains.member.domain.application.ports.output.message.publisher;

import com.shoes.ordering.system.domains.common.event.publisher.DomainEventPublisher;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberCreatedEvent;

public interface MemberCreatedRequestMessagePublisher extends DomainEventPublisher<MemberCreatedEvent> {
}
