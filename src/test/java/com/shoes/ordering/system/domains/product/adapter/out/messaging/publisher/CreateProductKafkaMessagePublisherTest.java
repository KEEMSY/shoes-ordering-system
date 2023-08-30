package com.shoes.ordering.system.domains.product.adapter.out.messaging.publisher;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.common.kafka.config.KafkaConfigData;
import com.shoes.ordering.system.common.kafka.config.KafkaConsumerConfigData;
import com.shoes.ordering.system.common.kafka.model.CreateProductRequestAvroModel;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"create-product-request"})
@SpringBootTest(classes = TestConfiguration.class)
class CreateProductKafkaMessagePublisherTest {
    @Autowired
    private CreateProductKafkaMessagePublisher createProductKafkaMessagePublisher;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private KafkaConfigData kafkaConfigData;
    @Autowired
    private KafkaConsumerConfigData kafkaConsumerConfigData;

    @Test
    @DisplayName("정상 publish 확인: ProductCreatedEvent 발행 확인")
    void publishTest() {
        // given
        String targetTopic = "create-product-request";

        Map<String, Object> consumerProps
                = KafkaTestUtils.consumerProps("testProductCreatedEventGroup", "false", embeddedKafkaBroker);

        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfigData.getKeyDeserializer());
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, kafkaConsumerConfigData.getValueDeserializer());
        consumerProps.put(kafkaConfigData.getSchemaRegistryUrlKey(), kafkaConfigData.getSchemaRegistryUrl());

        // when
        ProductCreatedEvent productCreatedEvent = createProductCreatedEvent();
        createProductKafkaMessagePublisher.publish(productCreatedEvent);

        // then
        DefaultKafkaConsumerFactory<String, CreateProductRequestAvroModel> consumerFactory
                = new DefaultKafkaConsumerFactory<>(consumerProps);

        Consumer<String, CreateProductRequestAvroModel> consumer = consumerFactory.createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, targetTopic);
        ConsumerRecord<String, CreateProductRequestAvroModel> record
                = KafkaTestUtils.getSingleRecord(consumer, targetTopic, 1000L);

        assertThat(record).isNotNull();
        assertThat(record.topic()).isEqualTo(targetTopic);
    }

    private ProductCreatedEvent createProductCreatedEvent() {
        Product product = Product.builder()
                .productCategory(ProductCategory.SHOES)
                .name("TestProductName1")
                .description("Test Product1 Description")
                .price(new Money(new BigDecimal("200.00")))
                .build();

        product.initializeProduct();

        return new ProductCreatedEvent(product, ZonedDateTime.now());
    }
}