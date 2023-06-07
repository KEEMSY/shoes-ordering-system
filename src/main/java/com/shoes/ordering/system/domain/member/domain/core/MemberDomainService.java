package com.shoes.ordering.system.domain.member.domain.core;

import com.shoes.ordering.system.domain.member.domain.core.entity.Member;
import com.shoes.ordering.system.domain.member.domain.core.event.MemberUpdatedEvent;
import com.shoes.ordering.system.domain.member.domain.core.event.MemberCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public interface MemberDomainService {
    MemberCreatedEvent validateAndInitiateMember(Member member);
    MemberUpdatedEvent updateMember(Member member);
}
