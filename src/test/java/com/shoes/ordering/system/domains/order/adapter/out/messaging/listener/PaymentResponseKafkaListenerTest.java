package com.shoes.ordering.system.domains.order.adapter.out.messaging.listener;

import com.shoes.ordering.system.CustomKafkaTestConfig;
import com.shoes.ordering.system.common.kafka.model.PaymentStatus;
import com.shoes.ordering.system.common.kafka.model.PaymentResponseAvroModel;

import com.shoes.ordering.system.TestConfiguration;



import com.shoes.ordering.system.domains.order.domain.application.dto.message.PaymentResponse;
import com.shoes.ordering.system.domains.order.domain.application.ports.input.message.listener.payment.PaymentResponseMessageListener;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Disabled;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@SpringBootTest(classes = TestConfiguration.class)
@EmbeddedKafka(partitions = 1, topics = "${order-service.payment-response-topic-name}")
@DirtiesContext
public class PaymentResponseKafkaListenerTest {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private CustomKafkaTestConfig customKafkaTestConfig;
    @MockBean
    private PaymentResponseMessageListener paymentResponseMessageListener;

    private Long TIMEOUT_LIMIT = 1000L;


    @Test
    @Disabled
    @DisplayName("정상 PaymentResponseKafkaListener 동작 확인: PaymentStatus.COMPLETED")
    public void testPaymentResponseListener() throws Exception {
        // given
        Map<String, Object> producerProps = customKafkaTestConfig.createDefaultProducerProps(embeddedKafkaBroker);
        KafkaTemplate<String, PaymentResponseAvroModel> kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerProps));

        PaymentResponseAvroModel paymentResponseAvroModel = createPaymentResponseAvroModel();

        kafkaTemplate.send(new ProducerRecord<>(
                "payment-response",
                0,
                UUID.randomUUID().toString(),
                paymentResponseAvroModel
        ));
        Thread.sleep(TIMEOUT_LIMIT);

        // when, then
        verify(paymentResponseMessageListener).paymentCompleted(any(PaymentResponse.class));


    }

    private PaymentResponseAvroModel createPaymentResponseAvroModel() {
        return PaymentResponseAvroModel.newBuilder()
                .setId(UUID.randomUUID())
                .setPaymentId(UUID.randomUUID())
                .setMemberId(UUID.randomUUID())
                .setOrderId(UUID.randomUUID())
                .setPrice(new BigDecimal("200.00"))
                .setCreatedAt(ZonedDateTime.now().toInstant())
                .setPaymentStatus(PaymentStatus.COMPLETED)
                .setFailureMessages(List.of("TEST"))
                .build();
    }

}
