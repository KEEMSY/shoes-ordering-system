package com.shoes.ordering.system.domains.payment.domain.application.ports.output.repository;

import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderId;
import com.shoes.ordering.system.domains.payment.domain.core.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findByOrderId(OrderId orderId);
}
