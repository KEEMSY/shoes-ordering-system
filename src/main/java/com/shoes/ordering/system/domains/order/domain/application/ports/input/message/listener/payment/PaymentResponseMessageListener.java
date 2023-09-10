package com.shoes.ordering.system.domains.order.domain.application.ports.input.message.listener.payment;

import com.shoes.ordering.system.domains.order.domain.application.dto.message.PaymentResponse;

public interface PaymentResponseMessageListener {
    void paymentCompleted(PaymentResponse paymentResponse);

    void paymentCancelled(PaymentResponse paymentResponse);
}
