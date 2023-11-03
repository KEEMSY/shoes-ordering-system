package com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository;

import com.shoes.ordering.system.domains.payment.domain.core.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findByOrderId(UUID orderId);
}
