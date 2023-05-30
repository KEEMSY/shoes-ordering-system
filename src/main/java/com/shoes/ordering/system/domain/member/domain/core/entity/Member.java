package com.shoes.ordering.system.domain.member.domain.core.entity;

import com.shoes.ordering.system.domain.common.entity.AggregateRoot;
import com.shoes.ordering.system.domain.common.valueobject.MemberId;
import com.shoes.ordering.system.domain.common.valueobject.MemberStatus;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.StreetAddress;
public class Member extends AggregateRoot<MemberId> {
    private final String name;
    private final String password;
    private final String email;
    private final MemberKind memberKind;
    private final String phoneNumber;
    private final StreetAddress address;
    private MemberStatus memberStatus;

    private Member(Builder builder) {
        super.setId(builder.memberId);
        name = builder.name;
        password = builder.password;
        email = builder.email;
        memberKind = builder.memberKind;
        phoneNumber = builder.phoneNumber;
        address = builder.address;
        memberStatus = builder.memberStatus;
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public MemberKind getMemberKind() {
        return memberKind;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public StreetAddress getAddress() {
        return address;
    }

    public MemberStatus getMemberStatus() {
        return memberStatus;
    }

    public static final class Builder {
        private MemberId memberId;
        private String name;
        private String password;
        private String email;
        private MemberKind memberKind;
        private String phoneNumber;
        private StreetAddress address;
        private MemberStatus memberStatus;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder memberId(MemberId val) {
            memberId = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder password(String val) {
            password = val;
            return this;
        }

        public Builder email(String val) {
            email = val;
            return this;
        }

        public Builder memberKind(MemberKind val) {
            memberKind = val;
            return this;
        }

        public Builder phoneNumber(String val) {
            phoneNumber = val;
            return this;
        }

        public Builder address(StreetAddress val) {
            address = val;
            return this;
        }

        public Builder memberStatus(MemberStatus val) {
            memberStatus = val;
            return this;
        }

        public Member build() {
            return new Member(this);
        }
    }
}
