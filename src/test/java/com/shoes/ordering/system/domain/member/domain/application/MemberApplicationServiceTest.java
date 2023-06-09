package com.shoes.ordering.system.domain.member.domain.application;

import com.shoes.ordering.system.domain.member.domain.application.dto.MemberAddress;
import com.shoes.ordering.system.domain.member.domain.application.dto.create.CreateMemberCommand;
import com.shoes.ordering.system.domain.member.domain.application.dto.create.CreateMemberResponse;
import com.shoes.ordering.system.domain.member.domain.application.dto.update.UpdateMemberCommand;
import com.shoes.ordering.system.domain.member.domain.application.dto.update.UpdateMemberResponse;
import com.shoes.ordering.system.domain.member.domain.application.mapper.MemberDataMapper;
import com.shoes.ordering.system.domain.member.domain.application.ports.input.service.MemberApplicationService;
import com.shoes.ordering.system.domain.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domain.member.domain.core.entity.Member;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = MemberTestConfiguration.class)
public class MemberApplicationServiceTest {

    @Autowired
    private MemberApplicationService memberApplicationService;

    @Autowired
    private MemberDataMapper memberDataMapper;

    @Autowired
    private MemberRepository memberRepository;

    private CreateMemberCommand createMemberCommand;

    private UpdateMemberCommand updateMemberCommand;

    private Member member;

    @BeforeEach
    public void init() {
        createMemberCommand = CreateMemberCommand.builder()
                .memberId(UUID.randomUUID())
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("0101231234")
                .name("TestName")
                .password("TestPassword123!@#")
                .email("testEmail@test.com")
                .address(new MemberAddress("123 Street", "99999", "City"))
                .build();

        member = memberDataMapper.createMemberCommandToMember(createMemberCommand);
        member.initializeMember();
        member.validateMember();


        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberRepository.findByMemberId(member.getId().getValue())).thenReturn(Optional.ofNullable(member));
    }

    @Test
    @DisplayName("정상 Member 생성 확인")
    public void createMemberTest() {
        // given: BeforeEach 에 포함됨

        // when
        CreateMemberResponse createMemberResponse = memberApplicationService.createMember(createMemberCommand);

        // then
        assertThat(createMemberResponse.getMemberStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    @DisplayName("정상 Member Update: PENDING To ACTIVATE")
    public void updateMemberTest() {
        // given
        updateMemberCommand = UpdateMemberCommand.builder()
                .memberId(member.getId().getValue())
                .name(member.getName())
                .password(member.getPassword())
                .email(member.getEmail())
                .memberStatus(MemberStatus.ACTIVATE)
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .build();

        // when
        UpdateMemberResponse updateMemberResponse = memberApplicationService.updateMember(updateMemberCommand);

        // then
        assertThat(updateMemberResponse.getMemberStatus()).isEqualTo(MemberStatus.ACTIVATE);
    }
}
