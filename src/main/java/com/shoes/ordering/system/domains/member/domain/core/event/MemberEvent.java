package com.shoes.ordering.system.domains.member.domain.core.event;

import com.shoes.ordering.system.domains.common.event.DomainEvent;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;

import java.time.ZonedDateTime;

public abstract class MemberEvent implements DomainEvent<Member> {
    private final Member member;
    private final ZonedDateTime createdAt;

    public MemberEvent(Member member, ZonedDateTime createdAt) {
        this.member = member;
        this.createdAt = createdAt;
    }

    public Member getMember() {
        return member;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
