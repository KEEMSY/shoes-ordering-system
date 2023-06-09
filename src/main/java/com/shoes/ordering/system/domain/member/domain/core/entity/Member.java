package com.shoes.ordering.system.domain.member.domain.core.entity;

import com.shoes.ordering.system.domain.common.entity.AggregateRoot;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberStatus;
import com.shoes.ordering.system.domain.member.domain.core.exception.MemberDomainException;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domain.common.valueobject.StreetAddress;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Member extends AggregateRoot<MemberId> {
    private final String name;
    private final String password;
    private final String email;
    private final MemberKind memberKind;
    private final String phoneNumber;
    private final StreetAddress address;
    private MemberStatus memberStatus;

    public static Builder builder() {
        return new Builder();
    }

    public void initializeMember() {
        setId(new MemberId(UUID.randomUUID()));
        memberStatus = MemberStatus.PENDING;
    }
    private void validateInitialMember() {
        if (memberStatus != MemberStatus.PENDING || getId() == null) {
            throw new MemberDomainException("Member is not in correct state for initialization");
        }
    }
    public void validateMember() {
        validateInitialMember();
        validateEmail();
        validatePassword();
    }

    public void validateUpdateMember() {
        validateInitialUpdateMember();
        validateEmail();
        validatePassword();
    }

    private void validateInitialUpdateMember() {
        List<MemberStatus> validMemberStatusList = Arrays.asList(MemberStatus.PENDING, MemberStatus.ACTIVATE);
        System.out.println("memberStatus in Member Obj:"+ memberStatus);
        if (!validMemberStatusList.contains(memberStatus) || getId() == null) {
            throw new MemberDomainException("Member is not in the correct state for updating initialization");
        }
    }

    private void validateEmail() {
        if (email == null || !isValidEmail(email)) {
            throw new MemberDomainException("Email is not valid!");
        }
    }

    private boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }

    private void validatePassword() {
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
        if (!password.matches(regex)) {
            throw new MemberDomainException("The password must be at least 8 characters long and contain at least one digit, one letter, and one special character");
        }
    }

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
