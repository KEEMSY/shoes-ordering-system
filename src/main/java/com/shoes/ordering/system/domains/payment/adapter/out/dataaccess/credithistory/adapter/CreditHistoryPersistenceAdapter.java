package com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.credithistory.adapter;

import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.credithistory.mapper.CreditHistoryDataAccessMapper;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.credithistory.repository.CreditHistoryJpaRepository;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository.CreditHistoryRepository;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditHistory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CreditHistoryPersistenceAdapter implements CreditHistoryRepository {
    private final CreditHistoryJpaRepository creditHistoryJpaRepository;
    private final CreditHistoryDataAccessMapper creditHistoryDataAccessMapper;

    public CreditHistoryPersistenceAdapter(CreditHistoryJpaRepository creditHistoryJpaRepository,
                                           CreditHistoryDataAccessMapper creditHistoryDataAccessMapper) {
        this.creditHistoryJpaRepository = creditHistoryJpaRepository;
        this.creditHistoryDataAccessMapper = creditHistoryDataAccessMapper;
    }

    @Override
    public CreditHistory save(CreditHistory creditHistory) {
        return null;
    }

    @Override
    public Optional<List<CreditHistory>> findByMemberId(MemberId memberId) {
        return Optional.empty();
    }
}
