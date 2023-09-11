package com.shoes.ordering.system.domains.member.domain.application.handler;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.member.domain.application.dto.MemberAddress;
import com.shoes.ordering.system.domains.member.domain.application.dto.create.CreateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.application.dto.create.CreateMemberResponse;
import com.shoes.ordering.system.domains.member.domain.application.mapper.MemberDataMapper;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.message.publisher.MemberCreatedRequestMessagePublisher;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberCreatedEvent;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberKind;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class MemberCreateCommandHandlerTest {

    @Autowired
    private MemberCreateCommandHandler memberCreateCommandHandler;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberDataMapper memberDataMapper;
    @MockBean
    private MemberCreatedRequestMessagePublisher memberCreatedRequestMessagePublisher;

    @Test
    @DisplayName("정상 CreateMemberResponse 생성 확인")
    void createMemberTest() {
        // given
        CreateMemberCommand createMemberCommand = CreateMemberCommand.builder()
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("0101231234")
                .name("TestName")
                .password("TestPassword123!@#")
                .email("testEmail@test.com")
                .address(new MemberAddress("123 Street", "99999", "City"))
                .build();

        Member member = memberDataMapper.createMemberCommandToMember(createMemberCommand);
        member.initializeMember();
        member.validateMember();

        when(memberRepository.save(any(Member.class))).thenReturn(member);

        // when
        CreateMemberResponse resultCreateMemberResponse = memberCreateCommandHandler.createMember(createMemberCommand);

        // then
        ArgumentCaptor<MemberCreatedEvent> capturedMemberCreatedEventCaptor
                = ArgumentCaptor.forClass(MemberCreatedEvent.class);
        verify(memberCreatedRequestMessagePublisher).publish(capturedMemberCreatedEventCaptor.capture());

        MemberCreatedEvent capturedMemberCreatedEvent = capturedMemberCreatedEventCaptor.getValue();
        assertThat(capturedMemberCreatedEvent.getMember().getId().getValue())
                .isEqualTo(resultCreateMemberResponse.getMemberId());
    }
}
