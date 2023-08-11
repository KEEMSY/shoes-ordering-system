package com.shoes.ordering.system.domains.member.domain.application.dto;

import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.application.dto.update.UpdateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UpdateMemberCommandTest {

    @Test
    @DisplayName("정상 UpdateMemberCommand 생성")
    void updateMemberCommandTest1() {
        // given
        UUID testMemberId = UUID.randomUUID();
        String testMemberName = "TestName";
        String testPhoneNumber = "0101231234";
        String testPassword = "TestPassword123!@#";
        String testEmail = "testEmail@test.com";
        MemberStatus testMemberStatus = MemberStatus.ACTIVATE;
        MemberKind testMemberKind = MemberKind.CUSTOMER;
        StreetAddress testMemberAddress = new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City");

        // when
        UpdateMemberCommand updateMemberCommand = UpdateMemberCommand.builder()
                .memberId(testMemberId)
                .memberStatus(testMemberStatus)
                .name(testMemberName)
                .phoneNumber(testPhoneNumber)
                .password(testPassword)
                .email(testEmail)
                .address(testMemberAddress)
                .memberKind(testMemberKind)
                .build();

        // then
        assertThat(updateMemberCommand).isNotNull();
        assertThat(updateMemberCommand.getMemberId()).isEqualTo(testMemberId);
    }

    @Test
    @DisplayName("비정상 UpdateMemberCommand 에러 확인: 프로퍼티 값에는 Null 이 올 수 없다.")
    void updateMemberCommandTest2() {
        // given
        String testMemberName = "TestName";
        String testPhoneNumber = "0101231234";
        String testPassword = "TestPassword123!@#";
        String testEmail = "testEmail@test.com";
        MemberStatus testMemberStatus = MemberStatus.ACTIVATE;
        MemberKind testMemberKind = MemberKind.CUSTOMER;
        StreetAddress testMemberAddress = new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City");

        // when, then
        assertThatThrownBy(() -> UpdateMemberCommand.builder()
                .memberStatus(testMemberStatus)
                .name(testMemberName)
                .phoneNumber(testPhoneNumber)
                .password(testPassword)
                .email(testEmail)
                .address(testMemberAddress)
                .memberKind(testMemberKind)
                .build()
        ).isInstanceOf(ConstraintViolationException.class);
    }
}
