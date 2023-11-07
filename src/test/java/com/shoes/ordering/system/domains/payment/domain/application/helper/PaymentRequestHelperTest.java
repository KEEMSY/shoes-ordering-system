package com.shoes.ordering.system.domains.payment.domain.application.helper;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.common.valueobject.PaymentOrderStatus;
import com.shoes.ordering.system.domains.common.valueobject.PaymentStatus;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderId;
import com.shoes.ordering.system.domains.payment.domain.application.dto.PaymentRequest;
import com.shoes.ordering.system.domains.payment.domain.application.exception.PaymentApplicationServiceException;
import com.shoes.ordering.system.domains.payment.domain.application.mapper.PaymentDataMapper;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository.CreditEntryRepository;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository.CreditHistoryRepository;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository.PaymentRepository;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditEntry;
import com.shoes.ordering.system.domains.payment.domain.core.entity.CreditHistory;
import com.shoes.ordering.system.domains.payment.domain.core.entity.Payment;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentCancelledEvent;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentCompletedEvent;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentEvent;
import com.shoes.ordering.system.domains.payment.domain.core.valueobject.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
class PaymentRequestHelperTest {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CreditEntryRepository creditEntryRepository;
    @Autowired
    private CreditHistoryRepository creditHistoryRepository;
    @Autowired
    private PaymentDataMapper paymentDataMapper;

    @Autowired
    private PaymentRequestHelper paymentRequestHelper;

    private final String stringOrderId = UUID.randomUUID().toString();
    private final String stringMemberId = UUID.randomUUID().toString();
    private final BigDecimal validPrice = new BigDecimal("50.00");

    @BeforeEach
    void setup() {
        // creditEntryRepository Mocking
        CreditEntry expectedCreditEntryResponse = createCreditEntry(validPrice);
        given(creditEntryRepository.findByMemberId(any(MemberId.class))).willReturn(Optional.of(expectedCreditEntryResponse));

        // creditHistoryRepository Mocking
        CreditHistory creditHistory = createCreditHistory(validPrice, TransactionType.CREDIT);
        List<CreditHistory> expectedCreditHistories = new ArrayList<>();
        expectedCreditHistories.add(creditHistory);
        given(creditHistoryRepository.findByMemberId(any(MemberId.class))).willReturn(Optional.of(expectedCreditHistories));
    }

    @Test
    @DisplayName("정상 Payment 저장 확인: PaymentCompletedEvent 확인")
    void persistPayment_CompletedOrderStatus_ShouldReturnCompletedEvent() {
        // given
        PaymentOrderStatus paymentOrderStatus = PaymentOrderStatus.PENDING;
        PaymentRequest paymentRequest = createPaymentRequest(stringMemberId, validPrice, paymentOrderStatus);

        // when
        PaymentEvent expectedPaymentEvent = paymentRequestHelper.persistPayment(paymentRequest);

        // then
        assertThat(expectedPaymentEvent).isNotNull().isInstanceOf(PaymentCompletedEvent.class);
    }

    @Test
    @DisplayName("정상 Payment 저장 에러 확인: CreditEntry 를 찾을 수 없을 경우")
    void persistPayment_CreditEntryNotFound_ShouldThrowException() {
        // given
        given(creditEntryRepository.findByMemberId(any(MemberId.class)))
                .willReturn(Optional.empty());

        PaymentOrderStatus paymentOrderStatus = PaymentOrderStatus.PENDING;
        String unknownMemberId = UUID.randomUUID().toString();
        PaymentRequest paymentRequest = createPaymentRequest(unknownMemberId, validPrice, paymentOrderStatus);

        // when, then
        assertThatThrownBy(() ->{ paymentRequestHelper.persistPayment(paymentRequest); })
                .isInstanceOf(PaymentApplicationServiceException.class)
                .hasMessage("Could not find credit for memberId: " + unknownMemberId);
    }

    @Test
    @DisplayName("정상 Payment 저장 에러 확인: CreditHistories 를 찾을 수 없을 경우")
    void persistPayment_CreditHistoriesNotFound_ShouldThrowException() {
        // given
        given(creditHistoryRepository.findByMemberId(any(MemberId.class)))
                .willReturn(Optional.empty());

        PaymentOrderStatus paymentOrderStatus = PaymentOrderStatus.PENDING;
        String unknownMemberId = UUID.randomUUID().toString();
        PaymentRequest paymentRequest = createPaymentRequest(unknownMemberId, validPrice, paymentOrderStatus);

        // when, then
        assertThatThrownBy(() ->{ paymentRequestHelper.persistPayment(paymentRequest); })
                .isInstanceOf(PaymentApplicationServiceException.class)
                .hasMessage("Could not find credit history for memberId: " + unknownMemberId);
    }

    @Test
    @DisplayName("정상 Payment 취소 저장 확인")
    void persisCancelPayment_ValidData_ShouldReturnCancelledEvent() {
        // given
        PaymentOrderStatus paymentOrderStatus = PaymentOrderStatus.CANCELLED;
        PaymentRequest paymentRequest = createPaymentRequest(stringMemberId, validPrice, paymentOrderStatus);

        Payment expectedPayment = paymentDataMapper.paymentRequestToPayment(paymentRequest);
        given(paymentRepository.findByOrderId(any(OrderId.class))).willReturn(Optional.of(expectedPayment));

        // when
        PaymentEvent expectedPaymentEvent = paymentRequestHelper.persisCancelPayment(paymentRequest);

        // then
        assertThat(expectedPaymentEvent).isNotNull().isInstanceOf(PaymentCancelledEvent.class);
        assertThat(expectedPayment.getPaymentStatus()).isEqualTo(PaymentStatus.CANCELLED);
    }

    @Test
    @DisplayName("정상 Payment 취소 간 저장 에러 확인: Payment 를 찾을 수 없을 경우")
    void persisCancelPayment_PaymentNotFound_ShouldThrowException() {
        // given
        given(paymentRepository.findByOrderId(any(OrderId.class))).willReturn(Optional.empty());

        PaymentOrderStatus paymentOrderStatus = PaymentOrderStatus.CANCELLED;
        PaymentRequest paymentRequest = createPaymentRequest(stringMemberId, validPrice, paymentOrderStatus);

        // when, then
        assertThatThrownBy(() -> {paymentRequestHelper.persisCancelPayment(paymentRequest);})
                .isInstanceOf(PaymentApplicationServiceException.class);
    }

    private CreditEntry createCreditEntry(BigDecimal totalCreditAmount) {
        return CreditEntry.builder()
                .memberId(new MemberId(UUID.fromString(stringMemberId)))
                .totalCreditAmount(new Money(totalCreditAmount))
                .build();
    }

    private PaymentRequest createPaymentRequest(String stringMemberId, BigDecimal price, PaymentOrderStatus paymentOrderStatus) {
        return PaymentRequest.builder()
                .id(UUID.randomUUID().toString())
                .orderId(stringOrderId)
                .memberId(stringMemberId)
                .price(price)
                .createdAt(Instant.now())
                .paymentOrderStatus(paymentOrderStatus)
                .build();
    }

    private CreditHistory createCreditHistory(BigDecimal amount, TransactionType transactionType) {
        return CreditHistory.builder()
                .memberId(new MemberId(UUID.fromString(stringMemberId)))
                .amount(new Money(amount))
                .transactionType(transactionType)
                .build();
    }
}