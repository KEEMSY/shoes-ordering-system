package com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.credithistory.mapper;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.credithistory.entity.CreditHistoryEntity;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditHistory;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.CreditHistoryId;
import org.springframework.stereotype.Component;

@Component
public class CreditHistoryDataAccessMapper {
    public CreditHistory creditHistoryEntityToCreditHistory(CreditHistoryEntity creditHistoryEntity) {
        return CreditHistory.builder()
                .creditHistoryId(new CreditHistoryId(creditHistoryEntity.getId()))
                .memberId(new MemberId(creditHistoryEntity.getMemberId()))
                .amount(new Money(creditHistoryEntity.getAmount()))
                .transactionType(creditHistoryEntity.getType())
                .build();
    }

    public CreditHistoryEntity creditHistoryToCreditHistoryEntity(CreditHistory creditHistory) {
        return CreditHistoryEntity.builder()
                .id(creditHistory.getId().getValue())
                .memberId(creditHistory.getMemberId().getValue())
                .amount(creditHistory.getAmount().getAmount())
                .type(creditHistory.getTransactionType())
                .build();
    }
}
