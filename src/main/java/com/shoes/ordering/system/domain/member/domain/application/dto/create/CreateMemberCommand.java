package com.shoes.ordering.system.domain.member.domain.application.dto.create;

import javax.validation.constraints.NotNull;

import com.shoes.ordering.system.domain.member.domain.application.dto.MemberAddress;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberKind;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateMemberCommand {
    @NotNull
    private final UUID memberId;

    @NotNull
    private final String name;

    @NotNull
    private final String password;

    @NotNull
    private final String email;

    @NotNull
    private final MemberKind memberKind;

    @NotNull
    private final String phoneNumber;

    @NotNull
    private MemberAddress address;
}
