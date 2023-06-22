package com.shoes.ordering.system.domain.member.domain.application.ports.output.message.publisher;

import com.shoes.ordering.system.domain.common.event.publisher.DomainEventPublisher;
import com.shoes.ordering.system.domain.member.domain.core.event.MemberUpdatedEvent;

public interface MemberUpdateRequestMessagePublisher extends DomainEventPublisher<MemberUpdatedEvent> {
}
