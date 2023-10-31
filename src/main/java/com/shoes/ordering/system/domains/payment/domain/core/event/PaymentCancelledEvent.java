package com.shoes.ordering.system.domains.payment.domain.core.event;

import com.shoes.ordering.system.domains.payment.domain.core.entity.Payment;

import java.time.ZonedDateTime;
import java.util.List;

public class PaymentCancelledEvent extends PaymentEvent{

    public PaymentCancelledEvent(Payment payment, ZonedDateTime createdAt, List<String> failureMessages) {
        super(payment, createdAt, failureMessages);
    }
}
