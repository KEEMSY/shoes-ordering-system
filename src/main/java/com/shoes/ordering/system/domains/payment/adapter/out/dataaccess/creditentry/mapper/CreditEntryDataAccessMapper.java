package com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.creditentry.mapper;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.creditentry.entity.CreditEntryEntity;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditEntry;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.CreditEntryId;
import org.springframework.stereotype.Component;

@Component
public class CreditEntryDataAccessMapper {
    public CreditEntry creditEntryEntityToCreditEntry(CreditEntryEntity creditEntryEntity) {
        return CreditEntry.builder()
                .id(new CreditEntryId(creditEntryEntity.getId()))
                .memberId(new MemberId(creditEntryEntity.getMemberId()))
                .totalCreditAmount(new Money(creditEntryEntity.getTotalCreditAmount()))
                .build();
    }

    public CreditEntryEntity creditEntryToCreditEntryEntity(CreditEntry creditEntry) {
        return CreditEntryEntity.builder()
                .id(creditEntry.getId().getValue())
                .memberId(creditEntry.getMemberId().getValue())
                .totalCreditAmount(creditEntry.getTotalCreditAmount().getAmount())
                .build();
    }
}
