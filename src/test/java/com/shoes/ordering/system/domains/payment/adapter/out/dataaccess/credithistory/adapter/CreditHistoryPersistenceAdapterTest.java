package com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.credithistory.adapter;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.credithistory.entity.CreditHistoryEntity;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.credithistory.mapper.CreditHistoryDataAccessMapper;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.credithistory.repository.CreditHistoryJpaRepository;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditHistory;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.CreditHistoryId;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
class CreditHistoryPersistenceAdapterTest {

    @Autowired
    CreditHistoryPersistenceAdapter creditHistoryPersistenceAdapter;
    @Autowired
    CreditHistoryJpaRepository creditHistoryJpaRepository;
    @Autowired
    CreditHistoryDataAccessMapper creditHistoryDataAccessMapper;

    private final UUID memberId = UUID.randomUUID();
    private final UUID creditHistoryId = UUID.randomUUID();
    private final BigDecimal validPrice = new BigDecimal("50.00");

    @AfterEach
    void clean() {
        creditHistoryJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("정상 CreditHistory 저장 확인")
    void save_ShouldReturnCreditHistory() {
        // given
        CreditHistory creditHistory = createCreditHistory(validPrice, TransactionType.CREDIT);

        // when
        CreditHistory savedCreditHistory = creditHistoryPersistenceAdapter.save(creditHistory);

        // then
        Optional<List<CreditHistoryEntity>> expectedCreditHistoryList = creditHistoryJpaRepository.findByMemberId(memberId);

        assertThat(savedCreditHistory).isEqualTo(creditHistory);
        assertThat(expectedCreditHistoryList).isPresent();
    }

    @Test
    @DisplayName("memberId 를 통한 정상 CreditHistory 조회 확인")
    void findByMemberId_ShouldReturnOptionalListCreditHistory() {
        // given
        CreditHistory creditHistory = createCreditHistory(validPrice, TransactionType.CREDIT);
        creditHistoryJpaRepository
                .save(creditHistoryDataAccessMapper.creditHistoryToCreditHistoryEntity(creditHistory));
        
        // when
        Optional<List<CreditHistory>> foundCreditHistoryList = creditHistoryPersistenceAdapter
                .findByMemberId(new MemberId(memberId));
        
        // then
        assertThat(foundCreditHistoryList).isPresent();
    }

    @Test
    @DisplayName("존재하지 않는 memberId 를 조회 시, CreditHistory 미존재 확인")
    void findByMemberId_ShouldReturnOptionalEmpty() {
        // given
        MemberId unknownMemberId = new MemberId(UUID.randomUUID());

        // when
        Optional<List<CreditHistory>> foundCreditHistoryList = creditHistoryPersistenceAdapter
                .findByMemberId(unknownMemberId);

        // then
        assertThat(foundCreditHistoryList.get().size()).isZero();
    }

    private CreditHistory createCreditHistory(BigDecimal amount, TransactionType transactionType) {
        return CreditHistory.builder()
                .creditHistoryId(new CreditHistoryId(creditHistoryId))
                .memberId(new MemberId(memberId))
                .amount(new Money(amount))
                .transactionType(transactionType)
                .build();
    }
}