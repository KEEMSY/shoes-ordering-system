package com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.payment.adapter;

import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderId;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.payment.mapper.PaymentDataAccessMapper;
import com.shoes.ordering.system.domains.payment.adapter.out.dataaccess.payment.repository.PaymentJpaRepository;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository.PaymentRepository;
import com.shoes.ordering.system.domains.payment.domain.core.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PaymentPersistenceAdapter implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentDataAccessMapper paymentDataAccessMapper;

    public PaymentPersistenceAdapter(PaymentJpaRepository paymentJpaRepository,
                                     PaymentDataAccessMapper paymentDataAccessMapper) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.paymentDataAccessMapper = paymentDataAccessMapper;
    }


    @Override
    public Payment save(Payment payment) {
        return paymentDataAccessMapper
                .paymentEntityToPayment(paymentJpaRepository
                        .save(paymentDataAccessMapper.paymentToPaymentEntity(payment)));
    }

    @Override
    public Optional<Payment> findByOrderId(OrderId orderId) {
        return null;
    }
}
