package com.shoes.ordering.system.domains.payment.adapter.messaging.listner.kafka;

import com.shoes.ordering.system.CustomKafkaTestConfig;
import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.common.kafka.model.PaymentOrderStatus;
import com.shoes.ordering.system.common.kafka.model.PaymentRequestAvroModel;
import com.shoes.ordering.system.domains.payment.domain.application.dto.PaymentRequest;
import com.shoes.ordering.system.domains.payment.domain.application.ports.input.message.listener.PaymentRequestMessageListener;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@EmbeddedKafka(partitions = 1, topics = {"payment-request"})
@SpringBootTest(classes = TestConfiguration.class)
@DirtiesContext
class PaymentRequestKafkaListenerTest {

//    @Autowired
    @MockBean
    private PaymentRequestMessageListener paymentRequestMessageListener;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private CustomKafkaTestConfig customKafkaTestConfig;

    static private final Long TIMEOUT_LIMIT = 1000L;

    @Test
    @DisplayName("정상 PaymentRequestKafkaListener 동작 확인: PENDING 상태 시, completePayment 수행 확인")
    void receive_DoCompletePayment() throws Exception {
        // given
        Map<String, Object> producerProps = customKafkaTestConfig.createDefaultProducerProps(embeddedKafkaBroker);
        KafkaTemplate<String, PaymentRequestAvroModel> kafkaTemplate
                = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerProps));

        PaymentRequestAvroModel paymentRequestAvroModel = createPaymentRequestAvroModel(PaymentOrderStatus.PENDING);

        kafkaTemplate.send(new ProducerRecord<>(
                "payment-request",
                0,
                UUID.randomUUID().toString(),
                paymentRequestAvroModel
        ));

        Thread.sleep(TIMEOUT_LIMIT);

        // when, then
        verify(paymentRequestMessageListener).completePayment(any(PaymentRequest.class));
    }

    @Test
    @DisplayName("정상 PaymentRequestKafkaListener 동작 확인: PENDING 상태 시, completePayment 수행 확인")
    void receive_DoCancelPayment() throws Exception {
        // given
        Map<String, Object> producerProps = customKafkaTestConfig.createDefaultProducerProps(embeddedKafkaBroker);
        KafkaTemplate<String, PaymentRequestAvroModel> kafkaTemplate
                = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerProps));

        PaymentRequestAvroModel paymentRequestAvroModel = createPaymentRequestAvroModel(PaymentOrderStatus.CANCELLED);

        kafkaTemplate.send(new ProducerRecord<>(
                "payment-request",
                0,
                UUID.randomUUID().toString(),
                paymentRequestAvroModel
        ));

        Thread.sleep(TIMEOUT_LIMIT);

        // when, then
        verify(paymentRequestMessageListener).cancelPayment(any(PaymentRequest.class));
    }

    private PaymentRequestAvroModel createPaymentRequestAvroModel(PaymentOrderStatus paymentOrderStatus) {
        return PaymentRequestAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setMemberId(UUID.randomUUID())
                .setOrderId(UUID.randomUUID())
                .setPrice(new BigDecimal("50.00"))
                .setCreatedAt(ZonedDateTime.now().toInstant())
                .setPaymentOrderStatus(paymentOrderStatus)
                .build();
    }

}