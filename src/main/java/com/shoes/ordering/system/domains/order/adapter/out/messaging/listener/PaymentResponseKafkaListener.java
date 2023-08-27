package com.shoes.ordering.system.domains.order.adapter.out.messaging.listener;

import com.shoes.ordering.system.common.kafka.consumer.KafkaConsumer;
import com.shoes.ordering.system.common.kafka.model.PaymentResponseAvroModel;
import com.shoes.ordering.system.common.kafka.model.PaymentStatus;
import com.shoes.ordering.system.domains.order.adapter.out.messaging.mapper.OrderMessagingDataMapper;
import com.shoes.ordering.system.domains.order.domain.application.dto.message.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class PaymentResponseKafkaListener implements KafkaConsumer<PaymentResponseAvroModel> {

    private final OrderMessagingDataMapper orderMessagingDataMapper;

    public PaymentResponseKafkaListener(OrderMessagingDataMapper orderMessagingDataMapper) {
        this.orderMessagingDataMapper = orderMessagingDataMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}", topics = "${order-service.payment-response-topic-name}")
    public void receive(@Payload List<PaymentResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of payment responses received with keys:{}, partitions:{} and offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(paymentResponseAvroModel -> {
            PaymentStatus paymentStatus = paymentResponseAvroModel.getPaymentStatus();
            UUID orderId = paymentResponseAvroModel.getOrderId();

            if (PaymentStatus.COMPLETED == paymentStatus) {
                logAndProcessSuccessfulPayment(orderId, paymentResponseAvroModel);
            } else if (PaymentStatus.CANCELLED == paymentStatus || PaymentStatus.FAILED == paymentStatus) {
                logAndProcessUnsuccessfulPayment(orderId, paymentResponseAvroModel);
            }
        });
    }
    private void logAndProcessSuccessfulPayment(UUID orderId, PaymentResponseAvroModel paymentResponseAvroModel) {
        log.info("Processing successful payment for order id: {}", orderId);
        PaymentResponse paymentResponse = orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel);
        // PaymentResponseMessageListener Logic: paymentCompleted()
    }

    private void logAndProcessUnsuccessfulPayment(UUID orderId, PaymentResponseAvroModel paymentResponseAvroModel) {
        log.info("Processing unsuccessful payment for order id: {}", orderId);
        PaymentResponse paymentResponse = orderMessagingDataMapper.paymentResponseAvroModelToPaymentResponse(paymentResponseAvroModel);
        // PaymentResponseMessageListener Logic: paymentCancelled()
    }
}
