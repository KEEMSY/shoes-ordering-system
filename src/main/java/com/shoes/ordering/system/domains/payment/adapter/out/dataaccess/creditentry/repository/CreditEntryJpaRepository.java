package com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.creditentry.repository;

import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.creditentry.entity.CreditEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CreditEntryJpaRepository extends JpaRepository<CreditEntryEntity, UUID> {

    Optional<CreditEntryEntity> findByMemberId(UUID memberId);


}
