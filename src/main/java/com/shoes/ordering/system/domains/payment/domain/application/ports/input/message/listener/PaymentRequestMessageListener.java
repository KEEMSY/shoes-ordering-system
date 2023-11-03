package com.shoes.ordering.system.domains.payment.domain.application.ports.input.message.listener;

import com.shoes.ordering.system.domains.payment.domain.application.dto.PaymentRequest;

public interface PaymentRequestMessageListener {

    void completePayment(PaymentRequest paymentRequest);

    void cancelPayment(PaymentRequest paymentRequest);
}
