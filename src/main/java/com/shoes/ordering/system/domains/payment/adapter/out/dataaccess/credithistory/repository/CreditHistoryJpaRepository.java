package com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.credithistory.repository;

import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.credithistory.entity.CreditHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CreditHistoryJpaRepository extends JpaRepository<CreditHistoryEntity, UUID> {
    Optional<List<CreditHistoryEntity>> findByMemberId(UUID memberId);
}
