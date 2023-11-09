package com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.creditentry.adapter;

import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.creditentry.mapper.CreditEntryDataAccessMapper;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.creditentry.repository.CreditEntryJpaRepository;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository.CreditEntryRepository;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditEntry;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreditEntryPersistenceAdapter implements CreditEntryRepository {
    private final CreditEntryJpaRepository creditEntryJpaRepository;
    private final CreditEntryDataAccessMapper creditEntryDataAccessMapper;

    public CreditEntryPersistenceAdapter(CreditEntryJpaRepository creditEntryJpaRepository,
                                         CreditEntryDataAccessMapper creditEntryDataAccessMapper) {
        this.creditEntryJpaRepository = creditEntryJpaRepository;
        this.creditEntryDataAccessMapper = creditEntryDataAccessMapper;
    }

    @Override
    public CreditEntry save(CreditEntry creditEntry) {
        return creditEntryDataAccessMapper
                .creditEntryEntityToCreditEntry(creditEntryJpaRepository
                        .save(creditEntryDataAccessMapper.creditEntryToCreditEntryEntity(creditEntry)));
    }

    @Override
    public Optional<CreditEntry> findByMemberId(MemberId memberId) {
        return creditEntryJpaRepository
                .findByMemberId(memberId.getValue())
                .map(creditEntryDataAccessMapper::creditEntryEntityToCreditEntry);
    }
}
