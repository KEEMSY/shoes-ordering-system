package com.shoes.ordering.system.domains.payment.domain.application.mapper;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderId;
import com.shoes.ordering.system.domains.payment.domain.application.dto.PaymentRequest;
import com.shoes.ordering.system.domains.payment.domain.core.entity.Payment;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PaymentDataMapper {
    public Payment paymentRequestToPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .memberId(new MemberId(UUID.fromString(paymentRequest.getMemberId())))
                .orderId(new OrderId(UUID.fromString(paymentRequest.getOrderId())))
                .price(new Money(paymentRequest.getPrice()))
                .build();
    }
}
