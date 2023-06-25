package com.shoes.ordering.system.domains.member.dataaccess.mapper;

import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.dataaccess.entity.MemberAddressEntity;
import com.shoes.ordering.system.domains.member.dataaccess.entity.MemberEntity;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static com.shoes.ordering.system.domains.member.domain.core.entity.Member.MESSAGE_DELIMITER;

// 도메인 객체에서 엔터티 객체를 만들고 도메인을 만들기 위해 매퍼가 필요하다.
@Component
public class MemberDataAccessMapper {

    public MemberEntity memberToMemberEntity(Member member) {
        MemberEntity memberEntity = MemberEntity.builder()
                .memberId(member.getId().getValue())
                .name(member.getName())
                .password(member.getPassword())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .memberStatus(member.getMemberStatus())
                .memberKind(member.getMemberKind())
                .memberAddress(streetAddressToMemberAddressEntity(member.getAddress()))
                .messages(member.getMessages() != null ?
                        String.join(MESSAGE_DELIMITER, member.getMessages()) : "")
                .build();
        memberEntity.getMemberAddress().setMember(memberEntity);

        return memberEntity;
    }
    
    public Member memberEntityToMember(MemberEntity memberEntity) {
        return Member.builder()
                .memberId(new MemberId(memberEntity.getMemberId()))
                .name(memberEntity.getName())
                .password(memberEntity.getPassword())
                .email(memberEntity.getEmail())
                .phoneNumber(memberEntity.getPhoneNumber())
                .memberStatus(memberEntity.getMemberStatus())
                .memberKind(memberEntity.getMemberKind())
                .address(memberAddressEntityToStreetAddress(memberEntity.getMemberAddress()))
                .messages(memberEntity.getMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(memberEntity.getMessages()
                                .split(MESSAGE_DELIMITER))))
                .build();
    }

    private StreetAddress memberAddressEntityToStreetAddress(MemberAddressEntity address) {
        return new StreetAddress(address.getId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity());
    }

    private MemberAddressEntity streetAddressToMemberAddressEntity(StreetAddress address) {
        return MemberAddressEntity.builder()
                .id(UUID.randomUUID())
                .street(address.getStreet())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .build();
    }
}
