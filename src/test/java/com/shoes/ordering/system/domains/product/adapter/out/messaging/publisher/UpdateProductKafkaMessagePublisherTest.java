package com.shoes.ordering.system.domains.product.adapter.out.messaging.publisher;

import com.shoes.ordering.system.CustomKafkaTestConfig;
import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.common.kafka.model.UpdateProductRequestAvroModel;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductUpdatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import org.apache.kafka.clients.consumer.Consumer;
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
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext
@EmbeddedKafka(partitions = 1, topics = {"update-product-request"})
@SpringBootTest(classes = TestConfiguration.class)
class UpdateProductKafkaMessagePublisherTest {
    @Autowired
    private UpdateProductKafkaMessagePublisher updateProductKafkaMessagePublisher;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private CustomKafkaTestConfig customKafkaTestConfig;
    private Long TIMEOUT_LIMIT = 1000L;

    @Test
    @DisplayName("정상 publish 확인: ProductUpdatedEvent 발행 확")
    void publishTest() {
        // given
        String targetTopic = "update-product-request";
        Map<String, Object> consumerProps = customKafkaTestConfig.createDefaultConsumerProps(embeddedKafkaBroker);

        DefaultKafkaConsumerFactory<String, UpdateProductRequestAvroModel> consumerFactory
                = new DefaultKafkaConsumerFactory<>(consumerProps);

        Consumer<String, UpdateProductRequestAvroModel> consumer = consumerFactory.createConsumer();

        // when
        ProductUpdatedEvent productUpdatedEvent = createProductUpdatedEvent();
        updateProductKafkaMessagePublisher.publish(productUpdatedEvent);

        // then

        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, targetTopic);
        ConsumerRecord<String, UpdateProductRequestAvroModel> record
                = KafkaTestUtils.getSingleRecord(consumer, targetTopic, TIMEOUT_LIMIT);

        assertThat(record).isNotNull();
        assertThat(record.topic()).isEqualTo(targetTopic);
    }

    private ProductUpdatedEvent createProductUpdatedEvent() {
        Product product = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .productCategory(ProductCategory.SHOES)
                .name("TestProductName1")
                .description("Test Product1 Description")
                .price(new Money(new BigDecimal("200.00")))
                .build();

        return new ProductUpdatedEvent(product, ZonedDateTime.now());
    }
}