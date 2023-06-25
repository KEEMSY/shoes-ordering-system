package com.shoes.ordering.system.domains.member.domain.core.event;

import com.shoes.ordering.system.domains.member.domain.core.entity.Member;

import java.time.ZonedDateTime;

public class MemberUpdatedEvent extends MemberEvent {
    public MemberUpdatedEvent(Member member, ZonedDateTime createdAt) {
        super(member, createdAt);
    }
}
