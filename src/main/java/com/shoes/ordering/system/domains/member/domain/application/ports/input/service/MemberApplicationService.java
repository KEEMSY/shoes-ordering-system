package com.shoes.ordering.system.domains.member.domain.application.ports.input.service;

import com.shoes.ordering.system.domains.member.domain.application.dto.create.CreateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.application.dto.create.CreateMemberResponse;
import com.shoes.ordering.system.domains.member.domain.application.dto.track.TrackMemberQuery;
import com.shoes.ordering.system.domains.member.domain.application.dto.track.TrackMemberResponse;
import com.shoes.ordering.system.domains.member.domain.application.dto.update.UpdateMemberCommand;
import com.shoes.ordering.system.domains.member.domain.application.dto.update.UpdateMemberResponse;

import javax.validation.Valid;


public interface MemberApplicationService {

    CreateMemberResponse createMember(@Valid CreateMemberCommand createMemberCommand);
    UpdateMemberResponse updateMember(@Valid UpdateMemberCommand updateMemberCommand);
    TrackMemberResponse trackMember(@Valid TrackMemberQuery trackMemberQuery);
}
