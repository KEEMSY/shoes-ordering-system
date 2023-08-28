package com.shoes.ordering.system;

import com.shoes.ordering.system.domains.member.domain.application.ports.output.message.publisher.MemberCreatedRequestMessagePublisher;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.message.publisher.MemberUpdateRequestMessagePublisher;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.message.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.repository.OrderRepository;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.message.publisher.ProductCreatedRequestMessagePublisher;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.message.publisher.ProductUpdatedRequestMessagePublisher;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;


import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.apache.avro.Schema;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;


@SpringBootApplication(scanBasePackages = "com.shoes.ordering.system")
public class TestConfiguration {

    @Bean
    public MemberRepository memberRepository() { return Mockito.mock(MemberRepository.class); }
    @Bean
    public MemberCreatedRequestMessagePublisher memberCreatedRequestMessagePublisher(){
        return Mockito.mock(MemberCreatedRequestMessagePublisher.class);
    }
    @Bean
    public MemberUpdateRequestMessagePublisher memberUpdateRequestMessagePublisher(){
        return Mockito.mock(MemberUpdateRequestMessagePublisher.class);
    }

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
        Schema paymentRequestAvroSchema = loadSchemaFromClasspath("avro/payment_request.avsc");
        Schema createProductAvroSchema = loadSchemaFromClasspath("avro/create_product_request.avsc");
        Schema createMemberAvroSchema = loadSchemaFromClasspath("avro/create_member_request.avsc");

        // 스키마 추가
        mockSchemaRegistryClient.register("payment-request", paymentRequestAvroSchema);
        mockSchemaRegistryClient.register("create-product-request", createProductAvroSchema);
        mockSchemaRegistryClient.register("create-member-request", createMemberAvroSchema);

        return mockSchemaRegistryClient;
    }

    private Schema loadSchemaFromClasspath(String schemaFilePath) {
        try {
            // 클래스 경로에서 스키마 파일을 읽어옴
            Resource resource = new ClassPathResource(schemaFilePath);
            InputStream inputStream = resource.getInputStream();

            // 읽어온 스키마 파일을 파서로 파싱
            Schema.Parser parser = new Schema.Parser();
            return parser.parse(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Avro schema from classpath: " + schemaFilePath, e);
        }
    }
}
