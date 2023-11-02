package com.shoes.ordering.system.domains.payment.domain.core.entity;

import com.shoes.ordering.system.domains.common.entity.BaseEntity;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.CreditEntryId;

public class CreditEntry extends BaseEntity<CreditEntryId> {
    private final MemberId memberId;
    private Money totalCreditAmount;

    private CreditEntry(Builder builder) {
         setId(builder.creditEntryId);
        memberId = builder.memberId;
        totalCreditAmount = builder.totalCreditAmount;
    }

    public void addCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.add(amount);
    }

    public void subtractCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.subtract(amount);
    }

    public static Builder builder() {
        return new Builder();
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public Money getTotalCreditAmount() {
        return totalCreditAmount;
    }

    public static final class Builder {
        private CreditEntryId creditEntryId;
        private MemberId memberId;
        private Money totalCreditAmount;

        private Builder() {
        }



        public Builder id(CreditEntryId val) {
            creditEntryId = val;
            return this;
        }

        public Builder memberId(MemberId val) {
            memberId = val;
            return this;
        }

        public Builder totalCreditAmount(Money val) {
            totalCreditAmount = val;
            return this;
        }

        public CreditEntry build() {
            return new CreditEntry(this);
        }
    }
}
