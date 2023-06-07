package com.shoes.ordering.system.domain.member.domain.application.dto.update;

import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UpdateMemberResponse {

    @NotNull
    private final UUID memberId;
    @NotNull
    private final MemberStatus memberStatus;
    @NotNull
    private final String message;
}
