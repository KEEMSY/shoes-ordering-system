package com.shoes.ordering.system.domains.payment.domain.core.entity;

import com.shoes.ordering.system.domains.common.entity.BaseEntity;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.CreditHistoryId;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.TransactionType;

public class CreditHistory extends BaseEntity<CreditHistoryId> {

    private final MemberId memberId;
    private final Money amount;
    private final TransactionType transactionType;

    private CreditHistory(Builder builder) {
         setId(builder.creditHistoryId);
        memberId = builder.memberId;
        amount = builder.amount;
        transactionType = builder.transactionType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public MemberId getMemberId() {
        return memberId;
    }

    public Money getAmount() {
        return amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public static final class Builder {
        private CreditHistoryId creditHistoryId;
        private MemberId memberId;
        private Money amount;
        private TransactionType transactionType;

        private Builder() {
        }



        public Builder creditHistoryId(CreditHistoryId val) {
            creditHistoryId = val;
            return this;
        }

        public Builder memberId(MemberId val) {
            memberId = val;
            return this;
        }

        public Builder amount(Money val) {
            amount = val;
            return this;
        }

        public Builder transactionType(TransactionType val) {
            transactionType = val;
            return this;
        }

        public CreditHistory build() {
            return new CreditHistory(this);
        }
    }
}
