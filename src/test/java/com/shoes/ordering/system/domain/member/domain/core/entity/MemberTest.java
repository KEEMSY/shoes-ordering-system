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

import static org.assertj.core.api.Assertions.assertThat;
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

        Assertions.assertNotNull(member.getId());
        Assertions.assertEquals(MemberStatus.PENDING, member.getMemberStatus());
        Assertions.assertDoesNotThrow(member::validateMember);
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

        Assertions.assertThrows(MemberDomainException.class, member::validateMember);
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

        Assertions.assertThrows(MemberDomainException.class, member::validateMember);
    }

    @Test
    @DisplayName("정상 approve 확인")
    public void testApprove1() {
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

        member.approve();
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.APPROVED);
    }

    @Test
    @DisplayName("비정상 approve 에러 확인")
    public void testApprove2() {

        assertThatThrownBy(() -> {
            Member member = Member.builder()
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
    @Test
    @DisplayName("정상 signUp 확인")
    public void testSignUp1() {
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

        member.approve();
        member.signUp();
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVATE);
    }
    @Test
    @DisplayName("비정상 signUp 에러 확인")
    public void testSignUp2() {

        assertThatThrownBy(() -> {
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

            member.signUp();
        }).isInstanceOf(MemberDomainException.class)
                .hasMessageContaining("Member is not in correct state for active!");
    }

    @Test
    @DisplayName("정상 주소변경 확인")
    public void testChangeStreetAddress1() {
        // given
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

        member.approve();
        member.signUp();

        // when
        StreetAddress newAddress = new StreetAddress(
                UUID.randomUUID(),
                "newStreet",
                "newPostalCode",
                "newCity"
        );
        member.changeStreetAddress(newAddress);

        // then
        assertThat(member.getAddress()).isEqualTo(newAddress);
    }

    @Test
    @DisplayName("동일한 주소로 변경 불가능처리")
    public void testChangeStreetAddress2() {
        assertThatThrownBy(() -> {
            // given
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

            member.approve();
            member.signUp();

            // when
            StreetAddress sameAddress = new StreetAddress(
                    UUID.randomUUID(), "123 Street", "99999", "City");

            member.changeStreetAddress(sameAddress);

            // then
        }).isInstanceOf(MemberDomainException.class)
                .hasMessageContaining("The new address is the same as the current address!");
    }

    @Test
    @DisplayName("정상 멤버 상태 변경 확인1")
    public void testChangeMemberStatus1() {
        // given
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

        member.approve();
        member.signUp();

        // when
        member.changeMemberStatus();

        // then
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.DEACTIVATE);
    }

    @Test
    @DisplayName("정상 멤버 상태 변경 확인2")
    public void testChangeMemberStatus2() {
        // given
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

        member.approve();
        member.signUp();

        // when
        member.changeMemberStatus();
        member.changeMemberStatus();

        // then
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.ACTIVATE);
    }

    @Test
    @DisplayName("정상 회원 탈퇴 확인1: 회원가입 대기")
    public void testWithdraw1() {
        // given
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

        // when
        member.withdraw();

        // then
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.WITHDRAWAL);
    }

    @Test
    @DisplayName("정상 회원 탈퇴 확인2: 회원가입 승인")
    public void testWithdraw2() {
        // given
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

        // when
        member.approve();
        member.withdraw();

        // then
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.WITHDRAWAL);
    }

    @Test
    @DisplayName("정상 회원 탈퇴 확인3: 회원가입 가입완료")
    public void testWithdraw3() {
        // given
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

        // when
        member.approve();
        member.signUp();
        member.withdraw();

        // then
        assertThat(member.getMemberStatus()).isEqualTo(MemberStatus.WITHDRAWAL);
    }
}