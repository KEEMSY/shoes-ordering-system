package com.shoes.ordering.system.domain.member.domain.application.dto.update;

import com.shoes.ordering.system.domain.member.domain.application.dto.MemberAddress;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UpdateMemberCommand {

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
    private final MemberStatus memberStatus;

    @NotNull
    private final String phoneNumber;

    @NotNull
    private MemberAddress address;
}
