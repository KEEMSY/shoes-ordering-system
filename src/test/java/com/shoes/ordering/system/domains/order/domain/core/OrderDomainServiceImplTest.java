package com.shoes.ordering.system.domains.order.domain.core;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.entity.OrderItem;
import com.shoes.ordering.system.domains.order.domain.core.entity.TrackingId;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCreatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderDomainServiceImplTest {
    private OrderDomainServiceImpl orderDomainService;
    private StreetAddress deliveryAddress;
    private Money orderPrice;

    private List<OrderItem> items;
    private TrackingId trackingId;
    private List<String> failureMessages;

    @BeforeEach
    void setUp() {
        deliveryAddress = new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City");
        items = new ArrayList<OrderItem>();
        trackingId = new TrackingId(UUID.randomUUID());
        failureMessages = new ArrayList<String>();

        items.add(createOrderItem());

        orderDomainService = new OrderDomainServiceImpl();
    }

    private OrderItem createOrderItem() {
        int quantity = 2;
        Money productPrice = new Money(new BigDecimal("100.00"));
        Product product = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .name("Test name")
                .productCategory(ProductCategory.SHOES)
                .description("Test Description")
                .price(productPrice)
                .build();

        orderPrice = new Money(new BigDecimal("00.00"));
        orderPrice = orderPrice.add(productPrice.multiply(quantity));

        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .price(productPrice)
                .subTotal(productPrice.multiply(quantity))
                .build();
    }

    @Test
    @DisplayName("정상 OrderCreatedEvent 생성 확인")
    void validateAndInitiateOrder() {
        // given
        Order order = Order.builder()
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .failureMessages(failureMessages)
                .build();

        // when
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order);

        // then
        assertThat(orderCreatedEvent).isNotNull();
        assertThat(orderCreatedEvent.getOrder()).isEqualTo(order);
        assertThat(orderCreatedEvent.getCreatedAt()).isNotNull();
    }

    @Test
    void payOrder() {
    }

    @Test
    void approveOrder() {
    }

    @Test
    void cancelOrderPayment() {
    }

    @Test
    void cancelOrder() {
    }
}