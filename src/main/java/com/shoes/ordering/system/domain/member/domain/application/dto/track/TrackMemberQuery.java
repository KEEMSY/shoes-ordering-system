package com.shoes.ordering.system.domain.member.domain.application.dto.track;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class TrackMemberQuery {

    @NotNull
    private final UUID memberId;
}
