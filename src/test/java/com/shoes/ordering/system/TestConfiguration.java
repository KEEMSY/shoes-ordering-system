package com.shoes.ordering.system;

import com.shoes.ordering.system.common.kafka.consumer.config.KafkaConsumerConfig;
import com.shoes.ordering.system.common.kafka.producer.KafkaProducerConfig;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.repository.OrderRepository;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import io.confluent.kafka.schemaregistry.avro.AvroSchema;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

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

        // 스키마 추가
        mockSchemaRegistryClient.register("payment-request", paymentRequestAvroSchema);

        return mockSchemaRegistryClient;
    }

    @Bean
    KafkaAvroSerializer kafkaAvroSerializer() throws IOException, RestClientException {
        return new KafkaAvroSerializer(schemaRegistryClient());
    }

    @Bean
    KafkaAvroDeserializer kafkaAvroDeserializer() throws IOException, RestClientException {
        return new KafkaAvroDeserializer(schemaRegistryClient(), kafkaConsumerConfig.consumerConfigs());
    }

    @Bean
    DefaultKafkaProducerFactory producerFactory() throws IOException, RestClientException {
        return new DefaultKafkaProducerFactory(
                kafkaProducerConfig.producerConfig(),
                new KafkaAvroSerializer(),
                kafkaAvroSerializer()
        );
    }

    @Bean
    public KafkaTemplate kafkaTemplate() throws IOException, RestClientException {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public static DefaultKafkaConsumerFactory consumerFactory() throws IOException, RestClientException {
        return new DefaultKafkaConsumerFactory<>(kafkaConsumerConfig.consumerConfigs());
    }

    @Bean
    public static ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory() throws IOException, RestClientException {
        ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
