package com.shoes.ordering.system.domain.member.domain.application.ports.input.service;

import com.shoes.ordering.system.domain.member.domain.application.dto.create.CreateMemberCommand;
import com.shoes.ordering.system.domain.member.domain.application.dto.create.CreateMemberResponse;
import com.shoes.ordering.system.domain.member.domain.application.dto.update.UpdateMemberCommand;
import com.shoes.ordering.system.domain.member.domain.application.dto.update.UpdateMemberResponse;

import javax.validation.Valid;


public interface MemberApplicationService {

    CreateMemberResponse createMember(@Valid CreateMemberCommand createMemberCommand);
    UpdateMemberResponse updateMember(@Valid UpdateMemberCommand updateMemberCommand);
}
