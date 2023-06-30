package com.shoes.ordering.system.domains.member.domain.application;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.member.domain.application.dto.MemberAddress;
import com.shoes.ordering.system.domains.member.domain.application.dto.create.CreateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.application.dto.create.CreateMemberResponse;
import com.shoes.ordering.system.domains.member.domain.application.dto.track.TrackMemberQuery;
import com.shoes.ordering.system.domains.member.domain.application.dto.track.TrackMemberResponse;
import com.shoes.ordering.system.domains.member.domain.application.dto.update.UpdateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.application.dto.update.UpdateMemberResponse;
import com.shoes.ordering.system.domains.member.domain.application.mapper.MemberDataMapper;
import com.shoes.ordering.system.domains.member.domain.application.ports.input.service.MemberApplicationService;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.member.domain.core.exception.MemberNotFoundException;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class MemberApplicationServiceTest {

    @Autowired
    private MemberApplicationService memberApplicationService;

    @Autowired
    private MemberDataMapper memberDataMapper;

    @Autowired
    private MemberRepository memberRepository;

    private CreateMemberCommand createMemberCommand;

    private UpdateMemberCommand updateMemberCommand;

    private TrackMemberQuery trackMemberQuery;

    private Member member;

    @BeforeEach
    public void init() {
        createMemberCommand = CreateMemberCommand.builder()
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
                .memberKind(MemberKind.CUSTOMER)
                .memberStatus(MemberStatus.ACTIVATE)
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .build();

        // when
        UpdateMemberResponse updateMemberResponse = memberApplicationService.updateMember(updateMemberCommand);

        // then
        assertThat(updateMemberResponse.getMemberStatus()).isEqualTo(MemberStatus.ACTIVATE);
    }

    @Test
    @DisplayName("비정상 Member Update: Member 가 존재하지 않는 경우")
    public void updateMemberTest2() {
        // given
        UUID newMemberId = UUID.randomUUID();

        // when
        updateMemberCommand = UpdateMemberCommand.builder()
                .memberId(newMemberId)
                .name(member.getName())
                .password(member.getPassword())
                .email(member.getEmail())
                .memberStatus(MemberStatus.ACTIVATE)
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber(member.getPhoneNumber())
                .address(member.getAddress())
                .build();

        // then
        assertThatThrownBy(() ->{
            UpdateMemberResponse updateMemberResponse = memberApplicationService.updateMember(updateMemberCommand);
        }).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    @DisplayName("정상 Member 추적")
    public void trackMemberTest1() {
        // given
        trackMemberQuery = TrackMemberQuery.builder().memberId(member.getId().getValue()).build();

        // when
        TrackMemberResponse trackMemberResponse = memberApplicationService.trackMember(trackMemberQuery);

        // then
        assertThat(trackMemberResponse.getMemberId()).isEqualTo(member.getId().getValue());
        assertThat(trackMemberResponse.getMemberStatus()).isEqualTo(member.getMemberStatus());

    }

    @Test
    @DisplayName("비정상 Member 추적: 존재하지 않는 Member")
    public void trackMemberTest2() {
        // given
        UUID newMemberId = UUID.randomUUID();

        // when
        trackMemberQuery = TrackMemberQuery.builder().memberId(newMemberId).build();

        // then
        assertThatThrownBy(() ->{
            TrackMemberResponse trackMemberResponse = memberApplicationService.trackMember(trackMemberQuery);
        }).isInstanceOf(MemberNotFoundException.class);
    }
}
