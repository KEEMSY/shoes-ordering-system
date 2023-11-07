package com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository;

import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditEntry;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface CreditEntryRepository {

    CreditEntry save(CreditEntry creditEntry);

    Optional<CreditEntry> findByMemberId(MemberId memberId);
}
