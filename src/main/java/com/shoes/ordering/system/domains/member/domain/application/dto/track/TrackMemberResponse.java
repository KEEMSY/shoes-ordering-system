package com.shoes.ordering.system.domains.member.domain.application.dto.track;

import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackMemberResponse {

    @NotNull
    private final UUID memberId;
    @NotNull
    private final MemberStatus memberStatus;
    private final List<String> messages;
}
