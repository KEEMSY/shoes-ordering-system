package com.shoes.ordering.system.domains.member.domain.application.dto;

import com.shoes.ordering.system.domains.member.domain.application.dto.create.CreateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberKind;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CreateMemberCommandTest {
    @Test
    @DisplayName("정상 CreateMemberCommand 생성")
    void createMemberCommandTest1() {
        // given
        String testMemberName = "TestName";
        String testPhoneNumber = "0101231234";
        String testPassword = "TestPassword123!@#";
        String testEmail = "testEmail@test.com";
        MemberKind testMemberKind = MemberKind.CUSTOMER;
        MemberAddress testMemberAddress = new MemberAddress("123 Street", "99999", "City");

        // when
        CreateMemberCommand createMemberCommand = CreateMemberCommand.builder()
                .name(testMemberName)
                .phoneNumber(testPhoneNumber)
                .password(testPassword)
                .email(testEmail)
                .address(testMemberAddress)
                .memberKind(testMemberKind)
                .build();

        // then
        assertThat(createMemberCommand).isNotNull();
        assertThat(createMemberCommand.getName()).isEqualTo(testMemberName);
    }

    @Test
    @DisplayName("비정상 CreateMemberCommand 에러 확인: 프로퍼티 값에는 Null 이 올 수 없다.")
    void createProductCommandTest2() {
        // given
        String testPhoneNumber = "0101231234";
        String testPassword = "TestPassword123!@#";
        String testEmail = "testEmail@test.com";
        MemberKind testMemberKind = MemberKind.CUSTOMER;
        MemberAddress testMemberAddress = new MemberAddress("123 Street", "99999", "City");


        // when, then
        assertThatThrownBy(() -> CreateMemberCommand.builder()
                .name(null)
                .phoneNumber(testPhoneNumber)
                .password(testPassword)
                .email(testEmail)
                .address(testMemberAddress)
                .memberKind(testMemberKind)
                .build()
        ).isInstanceOf(ConstraintViolationException.class);
    }
}
