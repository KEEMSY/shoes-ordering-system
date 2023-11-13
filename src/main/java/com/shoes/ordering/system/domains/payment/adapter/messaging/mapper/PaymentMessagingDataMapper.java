package com.shoes.ordering.system.domains.payment.adapter.messaging.mapper;

import com.shoes.ordering.system.domains.common.valueobject.PaymentOrderStatus;

import com.shoes.ordering.system.PaymentRequestAvroModel;
import com.shoes.ordering.system.domains.payment.domain.application.dto.PaymentRequest;
import org.springframework.stereotype.Component;

@Component
public class PaymentMessagingDataMapper {
    public PaymentRequest paymentRequestAvroModelToPaymentRequest(PaymentRequestAvroModel paymentRequestAvroModel) {
        return PaymentRequest.builder()
                .id(paymentRequestAvroModel.getId().toString())
                .memberId(paymentRequestAvroModel.getMemberId().toString())
                .orderId(paymentRequestAvroModel.getOrderId().toString())
                .price(paymentRequestAvroModel.getPrice())
                .createdAt(paymentRequestAvroModel.getCreatedAt())
                .paymentOrderStatus(PaymentOrderStatus.PENDING)
                .build();
    }
}
