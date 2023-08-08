package com.shoes.ordering.system.domains.member.messaging.mapper;

import com.shoes.ordering.system.common.kafka.model.*;
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
                .setPhoneNumber(member.getPhoneNumber())
                .setAddress(domainStreetAddressToAvroMemberAddress(member.getAddress()))
                .setMemberKind(domainMemberKindToAvroMemberKind(member.getMemberKind()))
                .setMemberStatus(domainMemberStatusToAvroMemberStatus(member.getMemberStatus()))
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
                .setPhoneNumber(member.getPhoneNumber())
                .setAddress(domainStreetAddressToAvroMemberAddress(member.getAddress()))
                .setMemberKind(domainMemberKindToAvroMemberKind(member.getMemberKind()))
                .setMemberStatus(domainMemberStatusToAvroMemberStatus(member.getMemberStatus()))
                .setCreatedAt(memberUpdatedEvent.getCreatedAt().toInstant())
                .build();
    }

    private MemberKind domainMemberKindToAvroMemberKind(com.shoes.ordering.system
                                                                .domains.member.domain
                                                                .core.valueobject
                                                                .MemberKind memberKind) {
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

    private MemberStatus domainMemberStatusToAvroMemberStatus(com.shoes.ordering.system
                                                                .domains.member.domain
                                                                .core.valueobject
                                                                .MemberStatus memberStatus) {
        MemberStatus avroMemberStatus;
        switch (memberStatus) {
            case PENDING:
                avroMemberStatus = MemberStatus.PENDING;
                break;
            case ACTIVATE:
                avroMemberStatus = MemberStatus.ACTIVATE;
                break;
            case DEACTIVATE:
                avroMemberStatus = MemberStatus.DEACTIVATE;
                break;
            default:
                throw new MemberDomainException("Unsupported MemberStatus: " + memberStatus);
        }
        return avroMemberStatus;
    }

    private MemberAddress domainStreetAddressToAvroMemberAddress(com.shoes.ordering.system
                                                                        .domains.common
                                                                        .valueobject
                                                                        .StreetAddress streetAddress) {
        return MemberAddress.newBuilder()
                .setCity(streetAddress.getCity())
                .setStreet(streetAddress.getStreet())
                .setPostalCode(streetAddress.getPostalCode())
                .build();
    }
}
