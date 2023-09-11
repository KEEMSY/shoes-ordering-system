package com.shoes.ordering.system.domains.order.adapter.out.messaging.publisher;

import com.shoes.ordering.system.CustomKafkaTestConfig;
import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.common.kafka.model.PaymentRequestAvroModel;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.entity.OrderItem;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCancelledEvent;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderStatus;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.TrackingId;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@EmbeddedKafka(partitions = 1, topics = {"payment-request"})
@SpringBootTest(classes = TestConfiguration.class)
@DirtiesContext
class CancelOrderKafkaMessagePublisherTest {

    @Autowired
    private CancelOrderKafkaMessagePublisher cancelOrderKafkaMessagePublisher;
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;
    @Autowired
    private CustomKafkaTestConfig customKafkaTestConfig;

    private Long TIMEOUT_LIMIT = 1000L;
    private BigDecimal totalPrice = new BigDecimal("00.00");
    private int productQuantity = 1;
    private BigDecimal productPrice = new BigDecimal("50.00");

    @Test
    @DisplayName("정상 publish 확인: OrderCancelledEvent 발행 확인")
    void publishTest() {
        // given
        String targetTopic = "payment-request";
        Map<String, Object> consumerProps = customKafkaTestConfig.createDefaultConsumerProps(embeddedKafkaBroker);

        DefaultKafkaConsumerFactory<String, PaymentRequestAvroModel> consumerFactory
                = new DefaultKafkaConsumerFactory<>(consumerProps);
        Consumer<String, PaymentRequestAvroModel> consumer = consumerFactory.createConsumer();

        // when
        OrderCancelledEvent orderCancelledEvent = createOrderCancelledEvent();
        cancelOrderKafkaMessagePublisher.publish(orderCancelledEvent);

        // then
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, targetTopic);
        ConsumerRecord<String, PaymentRequestAvroModel> record
                = KafkaTestUtils.getSingleRecord(consumer, targetTopic, TIMEOUT_LIMIT);


        assertThat(record).isNotNull();
        assertThat(record.topic()).isEqualTo(targetTopic);
    }

    private OrderCancelledEvent createOrderCancelledEvent() {
        OrderItem orderItem = createOrderItem(productQuantity, productPrice);
        Order order = createOrder(totalPrice, List.of(orderItem));
        order.initializeOrder();

        return new OrderCancelledEvent(order, ZonedDateTime.now());
    }

    private Order createOrder(BigDecimal totalPrice, List<OrderItem> items) {
        return Order.builder()
                .trackingId(new TrackingId(UUID.randomUUID()))
                .memberId(new MemberId(UUID.randomUUID()))
                .deliveryAddress(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .price(new Money(totalPrice))
                .items(items)
                .orderStatus(OrderStatus.PENDING)
                .build();
    }

    private OrderItem createOrderItem(int quantity, BigDecimal productPrice) {
        Product product = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .name("Test name")
                .productCategory(ProductCategory.SHOES)
                .description("Test Description")
                .price(new Money(productPrice))
                .build();

        totalPrice = totalPrice.add(productPrice.multiply(BigDecimal.valueOf(quantity)));

        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .price(new Money(productPrice))
                .subTotal(new Money(productPrice.multiply(BigDecimal.valueOf(quantity))))
                .build();
    }

}