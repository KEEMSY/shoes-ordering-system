package com.shoes.ordering.system.domain.member.domain.application;

import com.shoes.ordering.system.domain.member.domain.application.dto.create.CreateMemberCommand;
import com.shoes.ordering.system.domain.member.domain.application.dto.create.CreateMemberResponse;
import com.shoes.ordering.system.domain.member.domain.application.dto.track.TrackMemberQuery;
import com.shoes.ordering.system.domain.member.domain.application.dto.track.TrackMemberResponse;
import com.shoes.ordering.system.domain.member.domain.application.dto.update.UpdateMemberCommand;
import com.shoes.ordering.system.domain.member.domain.application.dto.update.UpdateMemberResponse;
import com.shoes.ordering.system.domain.member.domain.application.handler.MemberCreateCommandHandler;
import com.shoes.ordering.system.domain.member.domain.application.handler.MemberTrackCommandHandler;
import com.shoes.ordering.system.domain.member.domain.application.handler.MemberUpdateCommandHandler;
import com.shoes.ordering.system.domain.member.domain.application.ports.input.service.MemberApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
public class MemberApplicationServiceImpl implements MemberApplicationService {

    private final MemberCreateCommandHandler memberCreateCommandHandler;
    private final MemberUpdateCommandHandler memberUpdateCommandHandler;
    private final MemberTrackCommandHandler memberTrackCommandHandler;

    public MemberApplicationServiceImpl(MemberCreateCommandHandler memberCreateCommandHandler,
                                        MemberUpdateCommandHandler memberUpdateCommandHandler,
                                        MemberTrackCommandHandler memberTrackCommandHandler) {
        this.memberCreateCommandHandler = memberCreateCommandHandler;
        this.memberUpdateCommandHandler = memberUpdateCommandHandler;
        this.memberTrackCommandHandler = memberTrackCommandHandler;
    }

    @Override
    public CreateMemberResponse createMember(CreateMemberCommand createMemberCommand) {
        return memberCreateCommandHandler.createMember(createMemberCommand);
    }
    @Override
    public UpdateMemberResponse updateMember(UpdateMemberCommand updateMemberCommand) {
        return memberUpdateCommandHandler.updateMember(updateMemberCommand);
    }
    @Override
    public TrackMemberResponse trackMember(TrackMemberQuery trackMemberQuery) {
        return memberTrackCommandHandler.trackMember(trackMemberQuery);
    }
}
