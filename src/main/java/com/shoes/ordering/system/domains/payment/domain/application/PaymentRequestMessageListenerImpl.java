package com.shoes.ordering.system.domains.payment.domain.application;

import com.shoes.ordering.system.domains.payment.domain.application.dto.PaymentRequest;
import com.shoes.ordering.system.domains.payment.domain.application.helper.PaymentRequestHelper;
import com.shoes.ordering.system.domains.payment.domain.application.ports.input.message.listener.PaymentRequestMessageListener;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.message.publisher.PaymentCancelledMessagePublisher;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import com.shoes.ordering.system.domains.payment.domain.application.ports.output.message.publisher.PaymentFailedMessagePublisher;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentCancelledEvent;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentCompletedEvent;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentEvent;
import com.shoes.ordering.system.domains.payment.domain.core.event.PaymentFailedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final PaymentRequestHelper paymentRequestHelper;
    private final PaymentCompletedMessagePublisher paymentCompletedMessagePublisher;
    private final PaymentCancelledMessagePublisher paymentCancelledMessagePublisher;
    private final PaymentFailedMessagePublisher paymentFailedMessagePublisher;

    public PaymentRequestMessageListenerImpl(PaymentRequestHelper paymentRequestHelper,
                                             PaymentCompletedMessagePublisher paymentCompletedMessagePublisher,
                                             PaymentCancelledMessagePublisher paymentCancelledMessagePublisher,
                                             PaymentFailedMessagePublisher paymentFailedMessagePublisher) {
        this.paymentRequestHelper = paymentRequestHelper;
        this.paymentCompletedMessagePublisher = paymentCompletedMessagePublisher;
        this.paymentCancelledMessagePublisher = paymentCancelledMessagePublisher;
        this.paymentFailedMessagePublisher = paymentFailedMessagePublisher;
    }

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = paymentRequestHelper.persistPayment(paymentRequest);
        fireEvent(paymentEvent);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = paymentRequestHelper.persisCancelPayment(paymentRequest);
        fireEvent(paymentEvent);
    }

    private void fireEvent(PaymentEvent paymentEvent) {
        log.info("Publishing payment event with paymentId: {} and orderId: {}",
                paymentEvent.getPayment().getId().getValue(),
                paymentEvent.getPayment().getOrderId().getValue());

        if (paymentEvent instanceof PaymentCompletedEvent) {
            paymentCompletedMessagePublisher.publish((PaymentCompletedEvent) paymentEvent);
        } else if (paymentEvent instanceof PaymentCancelledEvent) {
            paymentCancelledMessagePublisher.publish((PaymentCancelledEvent) paymentEvent);
        } else if (paymentEvent instanceof PaymentFailedEvent) {
            paymentFailedMessagePublisher.publish((PaymentFailedEvent) paymentEvent);
        }
    }
}
