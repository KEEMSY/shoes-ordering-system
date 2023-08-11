package com.shoes.ordering.system.domains.member.domain.application.handler;

import com.shoes.ordering.system.domains.member.domain.application.dto.create.CreateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.application.dto.create.CreateMemberResponse;
import com.shoes.ordering.system.domains.member.domain.application.helper.MemberHelper;
import com.shoes.ordering.system.domains.member.domain.application.mapper.MemberDataMapper;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.message.publisher.MemberCreatedRequestMessagePublisher;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Component
public class MemberCreateCommandHandler {

    private final MemberHelper memberHelper;
    private final MemberCreatedRequestMessagePublisher memberCreatedRequestMessagePublisher;
    private final MemberDataMapper memberDataMapper;

    public MemberCreateCommandHandler(MemberHelper memberHelper,
                                      MemberCreatedRequestMessagePublisher memberCreatedRequestMessagePublisher,
                                      MemberDataMapper memberDataMapper) {
        this.memberHelper = memberHelper;
        this.memberCreatedRequestMessagePublisher = memberCreatedRequestMessagePublisher;
        this.memberDataMapper = memberDataMapper;
    }

    @Transactional
    public CreateMemberResponse createMember(CreateMemberCommand createMemberCommand) {
        MemberCreatedEvent memberCreatedEvent = memberHelper.persistMember(createMemberCommand);
        log.info("Member is created with id: {}",  memberCreatedEvent.getMember().getId().getValue());
        memberCreatedRequestMessagePublisher.publish(memberCreatedEvent);
        return memberDataMapper.memberToCreateMemberResponse(
                memberCreatedEvent.getMember(),
                "Member Created Successfully");
    }

}
