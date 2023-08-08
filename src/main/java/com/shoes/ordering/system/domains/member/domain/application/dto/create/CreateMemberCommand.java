package com.shoes.ordering.system.domains.member.domain.application.dto.create;

import javax.validation.constraints.NotNull;

import com.shoes.ordering.system.domains.common.validation.SelfValidating;
import com.shoes.ordering.system.domains.member.domain.application.dto.MemberAddress;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberKind;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CreateMemberCommand extends SelfValidating<CreateMemberCommand> {

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

    private CreateMemberCommand(Builder builder) {
        name = builder.name;
        password = builder.password;
        email = builder.email;
        memberKind = builder.memberKind;
        phoneNumber = builder.phoneNumber;
        address = builder.address;

        this.validateSelf(this);
    }
    public static Builder builder() {
        return new Builder();
    }
    public static final class Builder {
        private @NotNull String name;
        private @NotNull String password;
        private @NotNull String email;
        private @NotNull MemberKind memberKind;
        private @NotNull String phoneNumber;
        private @NotNull MemberAddress address;

        private Builder() {
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

        public Builder phoneNumber(@NotNull String val) {
            phoneNumber = val;
            return this;
        }

        public Builder address(@NotNull MemberAddress val) {
            address = val;
            return this;
        }

        public CreateMemberCommand build() {
            return new CreateMemberCommand(this);
        }
    }
}
