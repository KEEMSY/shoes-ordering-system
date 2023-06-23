package com.shoes.ordering.system.domains.member.domain.core;

import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberUpdatedEvent;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberCreatedEvent;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
public class MemberDomainServiceImpl implements MemberDomainService{

    private static final String UTC = "UTC";
    @Override
    public MemberCreatedEvent validateAndInitiateMember(Member member) {
        member.initializeMember();
        member.validateMember();
        log.info("Member With id: {} is initiated", member.getId().getValue());
        return new MemberCreatedEvent(member, ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public MemberUpdatedEvent updateMember(Member member) {
        member.validateUpdateMember();

        log.info("Member With id: {} is changed", member.getId().getValue());
        return new MemberUpdatedEvent(member, ZonedDateTime.now(ZoneId.of(UTC)));
    }
}
