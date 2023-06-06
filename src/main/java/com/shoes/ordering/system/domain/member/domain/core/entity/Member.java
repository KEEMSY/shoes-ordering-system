package com.shoes.ordering.system.domain.member.domain.core.entity;

import com.shoes.ordering.system.domain.common.entity.AggregateRoot;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberStatus;
import com.shoes.ordering.system.domain.member.domain.core.exception.MemberDomainException;
import com.shoes.ordering.system.domain.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domain.common.valueobject.StreetAddress;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Member extends AggregateRoot<MemberId> {
    private final String name;
    private String password;
    private final String email;
    private final MemberKind memberKind;
    private String phoneNumber;
    private StreetAddress address;
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

    private void validateEmail() {
        if (email == null || !isValidEmail(email)) {
            throw new MemberDomainException("Email is not valid!");
        }
    }
    public void approve() {
        if (memberStatus != MemberStatus.PENDING) {
            throw new MemberDomainException("Member is not in correct state for signUp!");
        }

        memberStatus = MemberStatus.APPROVED;
    }
    public void signUp() {
        if (memberStatus != MemberStatus.APPROVED) {
            throw new MemberDomainException("Member is not in correct state for active!");
        }
        memberStatus = MemberStatus.ACTIVATE;
    }
    public void changeStreetAddress(StreetAddress newStreetAddress){
        if (memberStatus != MemberStatus.ACTIVATE) {
            throw new MemberDomainException("Member is not in the correct state for changing address!");
        }
        if (address.equals(newStreetAddress)) {
            throw new MemberDomainException("The new address is the same as the current address!");
        }
        address = newStreetAddress;
    }
    public void withdraw() {
        memberStatus = MemberStatus.WITHDRAWAL;
    }

    public void changeMemberStatus() {
        if (memberStatus == MemberStatus.PENDING || memberStatus == MemberStatus.APPROVED) {
            throw new MemberDomainException("Member is not in the correct state for changing member status!");
        }
        memberStatus = (memberStatus == MemberStatus.ACTIVATE) ? MemberStatus.DEACTIVATE : MemberStatus.ACTIVATE;
    }

    public void changePassword(String newPassword){
        if (memberStatus != MemberStatus.ACTIVATE) {
            throw new MemberDomainException("Member is not in the correct state for changing password!");
        }

        if (password.equals(newPassword)) {
            throw new MemberDomainException("The new password is the same as the current password!");
        }
        password = newPassword;
    }
    public void changePhoneNumber(String newPhoneNumber) {
        if (memberStatus != MemberStatus.ACTIVATE) {
            throw new MemberDomainException("Member is not in the correct state for changing phone number!");
        }

        if (phoneNumber.equals(newPhoneNumber)) {
            throw new MemberDomainException("The new phoneNumber is the same as the current phoneNumber!");
        }
        phoneNumber = newPhoneNumber;
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
