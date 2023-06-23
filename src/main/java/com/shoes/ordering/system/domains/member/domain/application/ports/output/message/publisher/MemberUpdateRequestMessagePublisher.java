package com.shoes.ordering.system.domains.member.domain.application.ports.output.message.publisher;

import com.shoes.ordering.system.domains.common.event.publisher.DomainEventPublisher;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberUpdatedEvent;

public interface MemberUpdateRequestMessagePublisher extends DomainEventPublisher<MemberUpdatedEvent> {
}
