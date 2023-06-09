package com.shoes.ordering.system.domain.member.domain.application.mapper;

import com.shoes.ordering.system.domain.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domain.member.domain.application.dto.create.CreateMemberCommand;
import com.shoes.ordering.system.domain.member.domain.application.dto.create.CreateMemberResponse;
import com.shoes.ordering.system.domain.member.domain.application.dto.MemberAddress;
import com.shoes.ordering.system.domain.member.domain.application.dto.update.UpdateMemberCommand;
import com.shoes.ordering.system.domain.member.domain.application.dto.update.UpdateMemberResponse;
import com.shoes.ordering.system.domain.member.domain.core.entity.Member;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberId;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MemberDataMapper {

    public Member createMemberCommandToMember(CreateMemberCommand createMemberCommand) {
        return Member.builder()
                .memberId(new MemberId(createMemberCommand.getMemberId()))
                .name(createMemberCommand.getName())
                .password(createMemberCommand.getPassword())
                .email(createMemberCommand.getEmail())
                .memberKind(createMemberCommand.getMemberKind())
                .phoneNumber(createMemberCommand.getPhoneNumber())
                .address(memberAddressToStreetAddress(createMemberCommand.getAddress()))
                .build();

    }

    public Member updateMemberCommandToMember(UpdateMemberCommand updateMemberCommand) {
        return Member.builder()
                .memberId(new MemberId(updateMemberCommand.getMemberId()))
                .name(updateMemberCommand.getName())
                .password(updateMemberCommand.getPassword())
                .email(updateMemberCommand.getEmail())
                .memberStatus(updateMemberCommand.getMemberStatus())
                .memberKind(updateMemberCommand.getMemberKind())
                .phoneNumber(updateMemberCommand.getPhoneNumber())
                .address(updateMemberCommand.getAddress())
                .build();
    }
    private StreetAddress memberAddressToStreetAddress(MemberAddress address) {
        return new StreetAddress(
                UUID.randomUUID(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity()
        );
    }

    public CreateMemberResponse memberToCreateMemberResponse(Member member, String message) {
        return CreateMemberResponse.builder()
                .memberId(member.getId().getValue())
                .memberStatus(member.getMemberStatus())
                .message(message)
                .build();
    }

    public UpdateMemberResponse memberToUpdateMemberResponse(Member member) {
        return UpdateMemberResponse.builder()
                .memberId(member.getId().getValue())
                .memberStatus(member.getMemberStatus())
                .build();
    }
}
