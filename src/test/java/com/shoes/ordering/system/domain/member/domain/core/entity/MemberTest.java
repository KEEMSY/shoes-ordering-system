package com.shoes.ordering.system.domain.member.domain.core.entity;

import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberStatus;
import com.shoes.ordering.system.domain.member.domain.core.exception.MemberDomainException;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domain.common.valueobject.StreetAddress;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class MemberTest {
    @Test
    @DisplayName("정상 Memeber 생성 확인")
    public void testInitializeMember() {
        Member member = Member.builder()
                .memberId(new MemberId(UUID.randomUUID()))
                .memberStatus(MemberStatus.PENDING)
                .name("KEEMSY")
                .password("Password123!")
                .email("keemsy@example.com")
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("qwe123qwe!R")
                .address(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .build();

        member.initializeMember();

        assertThat(member.getId()).isNotNull();
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.PENDING);
        assertThatCode(member::validateMember).doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Email 유효성 검사 확인")
    public void testValidateMember_InvalidEmail() {
        Member member = Member.builder()
                .memberId(new MemberId(UUID.randomUUID()))
                .memberStatus(MemberStatus.PENDING)
                .name("KEEMSY")
                .password("Password123!")
                .email("invalidemail")
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("1234567890!")
                .address(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .build();

        assertThatThrownBy(member::validateMember).isInstanceOf(MemberDomainException.class);
    }

    @Test
    @DisplayName("Password 유효성 검증 확인")
    public void testValidateMember_InvalidPassword() {
        Member member = Member.builder()
                .memberId(new MemberId(UUID.randomUUID()))
                .memberStatus(MemberStatus.PENDING)
                .name("KEEMSY")
                .password("password")
                .email("KEEMSY@example.com")
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("1234567890")
                .address(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .build();

        assertThatThrownBy(member::validateMember).isInstanceOf(MemberDomainException.class);
    }
}