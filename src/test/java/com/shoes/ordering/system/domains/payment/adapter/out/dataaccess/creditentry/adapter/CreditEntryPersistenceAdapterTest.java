package com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.creditentry.adapter;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.creditentry.mapper.CreditEntryDataAccessMapper;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.creditentry.repository.CreditEntryJpaRepository;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditEntry;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.CreditEntryId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
class CreditEntryPersistenceAdapterTest {

    @Autowired
    CreditEntryPersistenceAdapter creditEntryPersistenceAdapter;
    @Autowired
    CreditEntryJpaRepository creditEntryJpaRepository;
    @Autowired
    CreditEntryDataAccessMapper creditEntryDataAccessMapper;

    private final MemberId memberId = new MemberId(UUID.randomUUID());
    private final Money totalCreditAmount = new Money(new BigDecimal("50.00"));

    @AfterEach
    void clean() {
        creditEntryJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("정상 CreditEntryEntity 저장 확인")
    void save_ShouldReturnCreditEntry() {
        // given
        CreditEntry creditEntry = createCreditEntry(totalCreditAmount);

        // when
        CreditEntry savedCreditEntry = creditEntryPersistenceAdapter.save(creditEntry);

        // then
        assertThat(savedCreditEntry).isEqualTo(creditEntry);
    }

    @Test
    @DisplayName("memberId 를 통한 정상 CreditEntryEntity 조회 확인")
    void findByMemberId_ShouldReturnOptionalCreditEntry() {
        // given
        CreditEntry creditEntry = createCreditEntry(totalCreditAmount);
        creditEntryJpaRepository.save(creditEntryDataAccessMapper.creditEntryToCreditEntryEntity(creditEntry));

        // when
        Optional<CreditEntry> foundCreditEntry = creditEntryPersistenceAdapter.findByMemberId(memberId);

        // then
        assertThat(foundCreditEntry).isPresent();
    }

    @Test
    @DisplayName("존재하지 않는 memberId 를 조회 시, CreditEntry 미존재 확인")
    void findByMemberId_ShouldReturnOptionalEmpty() {
        // given
        MemberId unknownMemberId = new MemberId(UUID.randomUUID());

        // when
        Optional<CreditEntry> foundCreditEntry = creditEntryPersistenceAdapter.findByMemberId(unknownMemberId);

        // then
        assertThat(foundCreditEntry).isEmpty();
    }

    private CreditEntry createCreditEntry(Money totalCreditAmount) {
        return CreditEntry.builder()
                .id(new CreditEntryId(UUID.randomUUID()))
                .memberId(memberId)
                .totalCreditAmount(totalCreditAmount)
                .build();
    }
}