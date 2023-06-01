package com.shoes.ordering.system.domain.member.domain.core.entity;

import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberStatus;
import com.shoes.ordering.system.domain.member.domain.core.exception.MemberDomainException;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domain.common.valueobject.StreetAddress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

//import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class MemberTest {
    @Test
    @DisplayName("정상 Memeber 생성 확인")
    public void testInitializeMember() {
        Member member = Member.Builder.builder()
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

        Assertions.assertNotNull(member.getId());
        Assertions.assertEquals(MemberStatus.PENDING, member.getMemberStatus());
        Assertions.assertDoesNotThrow(member::validateMember);
    }

    @Test
    @DisplayName("Email 유효성 검사 확인")
    public void testValidateMember_InvalidEmail() {
        Member member = Member.Builder.builder()
                .memberId(new MemberId(UUID.randomUUID()))
                .memberStatus(MemberStatus.PENDING)
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
                .memberId(new MemberId(UUID.randomUUID()))
                .memberStatus(MemberStatus.PENDING)
                .name("KEEMSY")
                .password("password")
                .email("KEEMSY@example.com")
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("1234567890")
                .address(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .build();

        Assertions.assertThrows(MemberDomainException.class, member::validateMember);
    }

    @Test
    @DisplayName("정상 approve 확인")
    public void testApprove1() {
        Member member = Member.Builder.builder()
                .memberId(new MemberId(UUID.randomUUID()))
                .memberStatus(MemberStatus.PENDING)
                .name("KEEMSY")
                .password("password")
                .email("KEEMSY@example.com")
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("1234567890")
                .address(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .build();

        member.approve();
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.APPROVED);
    }

    @Test
    @DisplayName("비정상 approve 에러 확인")
    public void testApprove2() {

        assertThatThrownBy(() -> {
            Member member = Member.Builder.builder()
                    .memberId(new MemberId(UUID.randomUUID()))
                    .memberStatus(MemberStatus.ACTIVATE)
                    .name("KEEMSY")
                    .password("password")
                    .email("KEEMSY@example.com")
                    .memberKind(MemberKind.CUSTOMER)
                    .phoneNumber("1234567890")
                    .address(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                    .build();
            member.approve();

        }).isInstanceOf(MemberDomainException.class)
                .hasMessageContaining("Member is not in correct state for signUp!");

    }
}