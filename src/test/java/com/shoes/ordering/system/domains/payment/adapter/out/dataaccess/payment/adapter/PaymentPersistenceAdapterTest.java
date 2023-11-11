package com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.payment.adapter;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderId;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.payment.entity.PaymentEntity;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.payment.mapper.PaymentDataAccessMapper;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.payment.repository.PaymentJpaRepository;
import com.shoes.ordering.system.domains.payment.domain.core.entity.Payment;
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
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
class PaymentPersistenceAdapterTest {

    @Autowired
    PaymentPersistenceAdapter paymentPersistenceAdapter;
    @Autowired
    PaymentJpaRepository paymentJpaRepository;
    @Autowired
    PaymentDataAccessMapper paymentDataAccessMapper;

    private final OrderId orderId = new OrderId(UUID.randomUUID());
    private final MemberId memberId = new MemberId(UUID.randomUUID());
    private final Money validPrice = new Money(new BigDecimal("50.00"));

    @AfterEach
    void clean() {
        paymentJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("정상 PaymentEntity 저장 확인")
    void save_ShouldReturnPayment() {
        // given
        Payment payment = createPayment(validPrice);

        // when
        Payment expectedPayment = paymentPersistenceAdapter.save(payment);

        // then
        Optional<PaymentEntity> expectedPaymentEntity = paymentJpaRepository.findByOrderId(orderId.getValue());

        assertThat(expectedPayment).isEqualTo(payment);
        assertThat(expectedPaymentEntity).isPresent();

    }

    @Test
    @DisplayName("orderId 를 통한 정상 Payment 조회 확인")
    void findByOrderId_ShouldReturnOptionalPayment() {
        // given
        Payment payment = createPayment(validPrice);
        paymentJpaRepository.save(paymentDataAccessMapper.paymentToPaymentEntity(payment));

        // when
        Optional<Payment> foundPayment = paymentPersistenceAdapter.findByOrderId(orderId);

        // then
        assertThat(foundPayment).isPresent();

    }

    @Test
    @DisplayName("존재하지 않는 orderId 를 조회 시, Payment 미존재 확인")
    void findByOrderId_ShouldReturnOptionalEmpty() {
        // given
        OrderId unknownOrderId = new OrderId(UUID.randomUUID());

        // when
        Optional<Payment> foundPayment = paymentPersistenceAdapter.findByOrderId(unknownOrderId);

        // then
        assertThat(foundPayment).isEmpty();
    }

    private Payment createPayment(Money price) {
        Payment payment = Payment.builder()
                .orderId(orderId)
                .memberId(memberId)
                .price(price)
                .build();

        payment.initializePayment();

        return payment;
    }
}