package com.shoes.ordering.system.domains.payment.domain;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.common.valueobject.PaymentStatus;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderId;
import com.shoes.ordering.system.domains.payment.domain.core.PaymentDomainService;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditEntry;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditHistory;
import com.shoes.ordering.system.domains.payment.domain.core.entity.Payment;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentEvent;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class paymentDomainServiceImplTest {

    private final PaymentDomainService paymentDomainService = new PaymentDomainServiceImpl();

    private final OrderId orderId = new OrderId(UUID.randomUUID());
    private final MemberId memberId = new MemberId(UUID.randomUUID());
    private final Money validPrice = new Money(new BigDecimal("50.00"));
    private final Money validTotalCreditAmount = new Money(new BigDecimal("100.00"));
    private List<CreditHistory> creditHistories;
    private List<String> failureMessages;
    @BeforeEach
    void setUp() {
        creditHistories = new ArrayList<>();
        failureMessages = new ArrayList<>();
    }

    @Test
    @DisplayName("정상 PaymentCompletedEvent 생성 확인")
    void validateAndInitiatePaymentTest() {
        // given
        Payment payment = createPayment(validPrice);
        CreditEntry creditEntry = createCreditEntry(validTotalCreditAmount);
        CreditHistory creditHistory = createCreditHistory(memberId, validTotalCreditAmount, TransactionType.CREDIT);
        creditHistories.add(creditHistory);

        // when
        PaymentEvent paymentCompletedEvent =  paymentDomainService
                .validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages);

        // then
        assertThat(paymentCompletedEvent).isNotNull();
        assertThat(payment.getId()).isNotNull();
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.COMPLETED);
        assertThat(creditEntry.getTotalCreditAmount().getAmount()).isEqualTo(new BigDecimal("50.00"));
    }

    @Test
    @DisplayName("정상 PaymentFailedEvent 생성 확인 1: Payment 의 Price 가 유효하지 않을 경우")
    void invalidPaymentPriceTest() {
        // given
        Money invalidPrice = new Money(new BigDecimal("0.00"));
        Payment payment = createPayment(invalidPrice);
        CreditEntry creditEntry = createCreditEntry(validTotalCreditAmount);
        CreditHistory creditHistory = createCreditHistory(memberId, validTotalCreditAmount, TransactionType.CREDIT);
        creditHistories.add(creditHistory);

        // when
        PaymentEvent paymentFailedEvent =  paymentDomainService
                .validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages);

        // then
        assertThat(paymentFailedEvent).isNotNull();
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.FAILED);
    }

    @Test
    @DisplayName("정상 PaymentFailedEvent 생성 확인 2: Payment 가 CreditEntry 보다 클 경우")
    void invalidPaymentTest() {
        // given
        Payment payment = createPayment(validPrice);

        Money invalidTotalCreditAmount = new Money(new BigDecimal("0.00"));
        CreditEntry creditEntry = createCreditEntry(invalidTotalCreditAmount);

        CreditHistory creditHistory = createCreditHistory(memberId, invalidTotalCreditAmount, TransactionType.CREDIT);
        creditHistories.add(creditHistory);

        // when
        PaymentEvent paymentFailedEvent =  paymentDomainService
                .validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages);

        // then
        assertThat(paymentFailedEvent).isNotNull();
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.FAILED);
    }

    @Test
    @DisplayName("정상 PaymentFailedEvent 생성 확인 3: CreditHistories 내, DEBIT 이 CREDIT 보다 큰 경우")
    void invalidCreditHistoryTest1() {
        // given
        Payment payment = createPayment(validPrice);

        Money invalidTotalCreditAmount = new Money(new BigDecimal("0.00"));
        CreditEntry creditEntry = createCreditEntry(invalidTotalCreditAmount);

        CreditHistory invalidCreditHistory = createCreditHistory(memberId, validTotalCreditAmount, TransactionType.DEBIT);
        CreditHistory creditHistory = createCreditHistory(memberId, invalidTotalCreditAmount, TransactionType.CREDIT);

        creditHistories.addAll(List.of(creditHistory, invalidCreditHistory));

        // when
        PaymentEvent paymentFailedEvent =  paymentDomainService
                .validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages);

        // then
        assertThat(paymentFailedEvent).isNotNull();
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.FAILED);
    }

    @Test
    @DisplayName("정상 PaymentFailedEvent 생성 확인 3: CreditHistories 의 내역과 CreditEntry 가 일치 하지 않는 경우")
    void invalidCreditHistoryTest2() {
        // given
        Payment payment = createPayment(validPrice);

        Money unknownCreditAmount = new Money(new BigDecimal("10.00"));
        CreditEntry creditEntry = createCreditEntry(validTotalCreditAmount);

        CreditHistory unknownCreditHistory = createCreditHistory(memberId, unknownCreditAmount, TransactionType.CREDIT);
        CreditHistory creditHistory = createCreditHistory(memberId, validTotalCreditAmount, TransactionType.CREDIT);

        creditHistories.addAll(List.of(creditHistory, unknownCreditHistory));

        // when
        PaymentEvent paymentFailedEvent =  paymentDomainService
                .validateAndInitiatePayment(payment, creditEntry, creditHistories, failureMessages);

        // then
        assertThat(paymentFailedEvent).isNotNull();
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.FAILED);
    }

    @Test
    @DisplayName("정상 PaymentCancelledEvent 생성 확인")
    void validateAndCancelPaymentTest() {
        // given
        Payment payment = createPayment(validPrice);
        CreditEntry creditEntry = createCreditEntry(validTotalCreditAmount);
        CreditHistory creditHistory = createCreditHistory(memberId, validTotalCreditAmount, TransactionType.CREDIT);
        creditHistories.add(creditHistory);

        // when
        PaymentEvent paymentCancelledEvent = paymentDomainService
                .validateAndCancelPayment(payment, creditEntry, creditHistories, failureMessages);

        // then
        assertThat(paymentCancelledEvent).isNotNull();
        assertThat(payment.getId()).isNotNull();
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.CANCELLED);
        assertThat(creditEntry.getTotalCreditAmount().getAmount())
                .isEqualTo(validTotalCreditAmount.add(validPrice).getAmount());
    }

    @Test
    @DisplayName("취소 요청 시, 정상 PaymentFailedEvent 생성 확인: Payment 가 유효하지 않음 경우")
    void validateAndCancelPaymentWithWrongPaymentTest() {
        // given
        Money invalidPrice = new Money(new BigDecimal("0.00"));
        Payment payment = createPayment(invalidPrice);
        CreditEntry creditEntry = createCreditEntry(validTotalCreditAmount);
        CreditHistory creditHistory = createCreditHistory(memberId, validTotalCreditAmount, TransactionType.CREDIT);
        creditHistories.add(creditHistory);

        // when
        PaymentEvent paymentFailedEvent = paymentDomainService
                .validateAndCancelPayment(payment, creditEntry, creditHistories, failureMessages);

        // then
        assertThat(paymentFailedEvent).isNotNull();
        assertThat(payment.getId()).isNotNull();
        assertThat(payment.getPaymentStatus()).isEqualTo(PaymentStatus.FAILED);
    }

    private CreditHistory createCreditHistory(MemberId memberId, Money amount, TransactionType transactionType) {
        return CreditHistory.builder()
                .memberId(memberId)
                .amount(amount)
                .transactionType(transactionType)
                .build();
    }

    private Payment createPayment(Money price) {
        return Payment.builder()
                .orderId(orderId)
                .memberId(memberId)
                .price(price)
                .build();
    }

    private CreditEntry createCreditEntry(Money totalCreditAmount) {
        return CreditEntry.builder()
                .memberId(memberId)
                .totalCreditAmount(totalCreditAmount)
                .build();
    }
}