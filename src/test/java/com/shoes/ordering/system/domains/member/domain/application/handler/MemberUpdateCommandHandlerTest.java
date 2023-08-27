package com.shoes.ordering.system.domains.member.domain.application.handler;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.application.dto.update.UpdateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.application.dto.update.UpdateMemberResponse;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.message.publisher.MemberUpdateRequestMessagePublisher;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberUpdatedEvent;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class MemberUpdateCommandHandlerTest {

    @Autowired
    private MemberUpdateCommandHandler memberUpdateCommandHandler;
    @Autowired
    private MemberRepository memberRepository;
    @MockBean
    private MemberUpdateRequestMessagePublisher memberUpdateRequestMessagePublisher;

    private Member member;

    @BeforeEach
    void init() {
        member = Member.builder()
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("0101231234")
                .name("TestName")
                .password("TestPassword123!@#")
                .email("testEmail@test.com")
                .address(new StreetAddress(UUID.randomUUID(),"123 Street", "99999", "City"))
                .build();

        member.initializeMember();
        member.validateMember();

        when(memberRepository.save(any(Member.class))).thenReturn(member);
        when(memberRepository.findByMemberId(member.getId().getValue())).thenReturn(Optional.ofNullable(member));
    }

    @Test
    @DisplayName("정상 UpdateMemberResponse 생성 확인")
    void createMemberTest() {
        // given
        UpdateMemberCommand updateMemberCommand = UpdateMemberCommand.builder()
                .memberId(member.getId().getValue())
                .memberKind(MemberKind.CUSTOMER)
                .memberStatus(MemberStatus.ACTIVATE)
                .phoneNumber("0101231234")
                .name("TestName")
                .password("TestPassword123!@#")
                .email("testEmail@test.com")
                .address(new StreetAddress(UUID.randomUUID(),"456 Street", "00000", "Updated City"))
                .build();


        // when
        UpdateMemberResponse resultUpdateMemberResponse = memberUpdateCommandHandler.updateMember(updateMemberCommand);

        // then
        ArgumentCaptor<MemberUpdatedEvent> capturedMemberUpdatedEventCaptor
                = ArgumentCaptor.forClass(MemberUpdatedEvent.class);
        verify(memberUpdateRequestMessagePublisher).publish(capturedMemberUpdatedEventCaptor.capture());

        MemberUpdatedEvent capturedMemberUpdatedEvent = capturedMemberUpdatedEventCaptor.getValue();
        assertThat(capturedMemberUpdatedEvent.getMember().getId().getValue())
                .isEqualTo(resultUpdateMemberResponse.getMemberId());
    }
}
