package com.shoes.ordering.system;

import com.shoes.ordering.system.common.kafka.consumer.config.KafkaConsumerConfig;
import com.shoes.ordering.system.common.kafka.producer.KafkaProducerConfig;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.repository.OrderRepository;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.message.publisher.ProductCreatedRequestMessagePublisher;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.message.publisher.ProductUpdatedRequestMessagePublisher;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import io.confluent.kafka.schemaregistry.avro.AvroSchema;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;


import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.io.IOException;


@SpringBootApplication(scanBasePackages = "com.shoes.ordering.system")
public class TestConfiguration {

    private final KafkaProducerConfig kafkaProducerConfig;

    private static KafkaConsumerConfig kafkaConsumerConfig;

    public TestConfiguration(KafkaProducerConfig kafkaProducerConfig,
                             KafkaConsumerConfig kafkaConsumerConfig) {
        this.kafkaProducerConfig = kafkaProducerConfig;
        this.kafkaConsumerConfig = kafkaConsumerConfig;
    }


    @Bean
    public MemberRepository memberRepository() { return Mockito.mock(MemberRepository.class); }
    @Bean
    public ProductRepository productRepository() { return Mockito.mock(ProductRepository.class); }
    @Bean
    public ProductCreatedRequestMessagePublisher productCreatedRequestMessagePublisher() {
        return Mockito.mock(ProductCreatedRequestMessagePublisher.class);
    }
    @Bean
    public ProductUpdatedRequestMessagePublisher productUpdatedRequestMessagePublisher() {
        return Mockito.mock(ProductUpdatedRequestMessagePublisher.class);
    }


    @Bean
    public OrderRepository orderRepository() { return Mockito.mock(OrderRepository.class); }
    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }
    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }


    // Kafka 설정
    // 테스트 진행 시, 스키마를 반드시 추가해 줘야함
    @Bean
    MockSchemaRegistryClient schemaRegistryClient() throws IOException, RestClientException {
        MockSchemaRegistryClient mockSchemaRegistryClient = new MockSchemaRegistryClient();

        // 스키마 생성
        AvroSchema paymentRequestAvroSchema = new AvroSchema("{\"type\":\"record\",\"name\":\"PaymentRequestAvroModel\",\"namespace\":\"com.shoes.ordering.system\",\"fields\":[{\"name\":\"id\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"memberId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"orderId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"price\",\"type\":{\"type\":\"bytes\",\"logicalType\":\"decimal\",\"precision\":10,\"scale\":2}},{\"name\":\"createdAt\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}},{\"name\":\"paymentOrderStatus\",\"type\":{\"type\":\"enum\",\"name\":\"PaymentOrderStatus\",\"symbols\":[\"PENDING\",\"CANCELLED\"]}}]}");
        AvroSchema createProductAvroSchema = new AvroSchema("{\"type\":\"record\",\"name\":\"CreateMemberRequestAvroModel\",\"namespace\":\"com.shoes.ordering.system\",\"fields\":[{\"name\":\"memberId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"password\",\"type\":\"string\"},{\"name\":\"email\",\"type\":\"string\"},{\"name\":\"memberKind\",\"type\":{\"type\":\"enum\",\"name\":\"MemberKind\",\"symbols\":[\"CUSTOMER\",\"SELLER\",\"ADMIN\"]}},{\"name\":\"memberStatus\",\"type\":{\"type\":\"enum\",\"name\":\"MemberStatus\",\"symbols\":[\"PENDING\",\"ACTIVATE\",\"DEACTIVATE\",\"WITHDRAWAL\"]}},{\"name\":\"phoneNumber\",\"type\":\"string\"},{\"name\":\"address\",\"type\":{\"type\":\"record\",\"name\":\"MemberAddress\",\"fields\":[{\"name\":\"street\",\"type\":\"string\"},{\"name\":\"city\",\"type\":\"string\"},{\"name\":\"state\",\"type\":\"string\"},{\"name\":\"postalCode\",\"type\":\"string\"}]}},{\"name\":\"createdAt\",\"type\":{\"type\":\"long\",\"logicalType\":\"timestamp-millis\"}}]}");

        // 스키마 추가
        mockSchemaRegistryClient.register("payment-request", paymentRequestAvroSchema);
        mockSchemaRegistryClient.register("create-product-request", createProductAvroSchema);

        return mockSchemaRegistryClient;
    }
}
