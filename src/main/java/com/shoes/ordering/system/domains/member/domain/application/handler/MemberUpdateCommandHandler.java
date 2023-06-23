package com.shoes.ordering.system.domains.member.domain.application.handler;

import com.shoes.ordering.system.domains.member.domain.application.dto.update.UpdateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.application.dto.update.UpdateMemberResponse;
import com.shoes.ordering.system.domains.member.domain.application.helper.MemberHelper;
import com.shoes.ordering.system.domains.member.domain.application.mapper.MemberDataMapper;
import com.shoes.ordering.system.domains.member.domain.core.event.MemberUpdatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemberUpdateCommandHandler {
    private final MemberHelper memberHelper;
    private final MemberDataMapper memberDataMapper;

    public MemberUpdateCommandHandler(MemberHelper memberHelper, MemberDataMapper memberDataMapper) {
        this.memberHelper = memberHelper;
        this.memberDataMapper = memberDataMapper;
    }

    public UpdateMemberResponse updateMember(UpdateMemberCommand updateMemberCommand) {
        MemberUpdatedEvent memberUpdatedEvent = memberHelper.updateMemberPersist(updateMemberCommand);
        log.info("Member is updated with id: {}", memberUpdatedEvent.getMember().getId().getValue());
        return memberDataMapper.memberToUpdateMemberResponse(memberUpdatedEvent.getMember());
    }
}
