package com.shoes.ordering.system.domain.member.domain.core.entity;

import com.shoes.ordering.system.domain.common.valueobject.MemberId;
import com.shoes.ordering.system.domain.common.valueobject.MemberStatus;
import com.shoes.ordering.system.domain.member.domain.core.exception.MemberDomainException;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.StreetAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class MemberTest {
    @Test
    @DisplayName("정상 Memeber 생성 확인")
    public void testInitializeMember() {
        Member member = Member.Builder.builder()
                .memberId(new MemberId(UUID.randomUUID()))
                .name("KEEMSY")
                .password("Password123!")
                .email("keemsy@example.com")
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("qwe123qwe!R")
                .address(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .build();

        member.initializeMember();

        Assertions.assertNotNull(member.getId());
        Assertions.assertEquals(MemberStatus.PENDING, member.getMemberStatus());
        Assertions.assertDoesNotThrow(member::validateMember);
    }

    @Test
    @DisplayName("Email 유효성 검사 확인")
    public void testValidateMember_InvalidEmail() {
        Member member = Member.Builder.builder()
                .memberId(new MemberId(UUID.randomUUID()))
                .name("KEEMSY")
                .password("Password123!")
                .email("invalidemail")
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("1234567890!")
                .address(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .build();

        Assertions.assertThrows(MemberDomainException.class, member::validateMember);
    }

    @Test
    @DisplayName("Password 유효성 검증 확인")
    public void testValidateMember_InvalidPassword() {
        Member member = Member.Builder.builder()
                .name("KEEMSY")
                .password("password")
                .email("KEEMSY@example.com")
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("1234567890")
                .address(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .build();

        Assertions.assertThrows(MemberDomainException.class, member::validateMember);
    }

}