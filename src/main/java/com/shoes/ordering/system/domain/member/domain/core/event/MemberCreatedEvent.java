package com.shoes.ordering.system.domain.member.domain.core.event;

import com.shoes.ordering.system.domain.member.domain.core.entity.Member;

import java.time.ZonedDateTime;

public class MemberCreatedEvent extends MemberEvent{
    public MemberCreatedEvent(Member member, ZonedDateTime createdAt) {
        super(member, createdAt);
    }
}
