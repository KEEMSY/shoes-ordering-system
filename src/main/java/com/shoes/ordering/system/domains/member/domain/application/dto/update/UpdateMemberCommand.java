package com.shoes.ordering.system.domains.member.domain.application.dto.update;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateMemberCommand extends SelfValidating<UpdateMemberCommand> {

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
    private StreetAddress address;

    private UpdateMemberCommand(Builder builder) {
        memberId = builder.memberId;
        name = builder.name;
        password = builder.password;
        email = builder.email;
        memberKind = builder.memberKind;
        memberStatus = builder.memberStatus;
        phoneNumber = builder.phoneNumber;
        address = builder.address;

        this.validateSelf(this);
    }

    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private @NotNull UUID memberId;
        private @NotNull String name;
        private @NotNull String password;
        private @NotNull String email;
        private @NotNull MemberKind memberKind;
        private @NotNull MemberStatus memberStatus;
        private @NotNull String phoneNumber;
        private @NotNull StreetAddress address;

        private Builder() {
        }



        public Builder memberId(@NotNull UUID val) {
            memberId = val;
            return this;
        }

        public Builder name(@NotNull String val) {
            name = val;
            return this;
        }

        public Builder password(@NotNull String val) {
            password = val;
            return this;
        }

        public Builder email(@NotNull String val) {
            email = val;
            return this;
        }

        public Builder memberKind(@NotNull MemberKind val) {
            memberKind = val;
            return this;
        }

        public Builder memberStatus(@NotNull MemberStatus val) {
            memberStatus = val;
            return this;
        }

        public Builder phoneNumber(@NotNull String val) {
            phoneNumber = val;
            return this;
        }

        public Builder address(@NotNull StreetAddress val) {
            address = val;
            return this;
        }

        public UpdateMemberCommand build() {
            return new UpdateMemberCommand(this);
        }
    }
}
