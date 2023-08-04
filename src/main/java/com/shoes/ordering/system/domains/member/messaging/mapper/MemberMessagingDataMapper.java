package com.shoes.ordering.system.domains.member.messaging.mapper;

import com.shoes.ordering.system.common.kafka.model.CreateMemberRequestAvroModel;
import com.shoes.ordering.system.common.kafka.model.MemberKind;
import com.shoes.ordering.system.common.kafka.model.MemberStatus;
import com.shoes.ordering.system.common.kafka.model.UpdateMemberRequestAvroModel;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberCreatedEvent;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberUpdatedEvent;
import com.shoes.ordering.system.domains.member.domain.core.exception.MemberDomainException;
import org.springframework.stereotype.Component;

@Component
public class MemberMessagingDataMapper {

    public CreateMemberRequestAvroModel memberCreatedEventToCreateMemberRequestAvroModel(MemberCreatedEvent memberCreatedEvent) {
        Member member = memberCreatedEvent.getMember();
        return CreateMemberRequestAvroModel.newBuilder()
                .setMemberId(member.getId().getValue())
                .setName(member.getName())
                .setPassword(member.getPassword())
                .setEmail(member.getEmail())
                .setMemberKind(domainMemberKindToAvroMemberKind(member.getMemberKind()))
                .setMemberStatus(MemberStatus.PENDING)
                .setCreatedAt(memberCreatedEvent.getCreatedAt().toInstant())
                .build();
    }

    public UpdateMemberRequestAvroModel memberUpdatedEventToUpdateMemberRequestAvroModel(MemberUpdatedEvent memberUpdatedEvent) {
        Member member = memberUpdatedEvent.getMember();
        return UpdateMemberRequestAvroModel.newBuilder()
                .setMemberId(member.getId().getValue())
                .setName(member.getName())
                .setPassword(member.getPassword())
                .setEmail(member.getEmail())
                .setMemberKind(domainMemberKindToAvroMemberKind(member.getMemberKind()))
                .setMemberStatus(MemberStatus.PENDING)
                .setCreatedAt(memberUpdatedEvent.getCreatedAt().toInstant())
                .build();
    }

    private MemberKind domainMemberKindToAvroMemberKind(com.shoes.ordering.system
                                                                .domains.member.domain
                                                                .core.valueobject.MemberKind memberKind) {
        MemberKind avroMemberKind;
        switch (memberKind) {
            case CUSTOMER:
                avroMemberKind = MemberKind.CUSTOMER;
                break;
            case SELLER:
                avroMemberKind = MemberKind.SELLER;
                break;
            case ADMIN:
                avroMemberKind = MemberKind.ADMIN;
                break;
            default:
                throw new MemberDomainException("Unsupported MemberKind: " + memberKind);
        }
        return avroMemberKind;
    }
}
