package com.shoes.ordering.system.domains.order.domain.core.entity;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderId;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderItemId;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class OrderItemTest {

    private OrderId orderId;
    private OrderItemId orderItemId;
    private Product product;
    private int quantity;
    private Money price;
    private Money subTotal;

    @BeforeEach
    public void setUp() {
        orderId = new OrderId(UUID.randomUUID());
        orderItemId = new OrderItemId(1L);
        quantity = 2;
        price = new Money(new BigDecimal("100.00"));
        subTotal = price.multiply(quantity);

        product = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .name("Test name")
                .productCategory(ProductCategory.SHOES)
                .description("Test Description")
                .price(price)
                .build();

        product.initializeProduct();
        product.validateProduct();


    }

    @Test
    @DisplayName("OrderItem 생성 및 초기화 확인")
    public void testInitializeOrderItem() {
        // given
        OrderItem orderItem = OrderItem.builder()
                .orderItemId(orderItemId)
                .product(product)
                .quantity(quantity)
                .price(price)
                .subTotal(subTotal)
                .build();

        // when
        orderItem.initializeOrderItem(orderId, orderItemId);

        // then
        assertThat(orderItem.getOrderId()).isEqualTo(orderId);
        assertThat(orderItem.getId()).isEqualTo(orderItemId);
    }

    @Test
    @DisplayName("OrderItem 가격 유효성 확인")
    public void testIsPriceValid() {
        // given
        OrderItem orderItem = OrderItem.builder()
                .orderItemId(orderItemId)
                .product(product)
                .quantity(quantity)
                .price(price)
                .subTotal(subTotal)
                .build();

        // when, then
        assertThat(orderItem.isPriceValid()).isTrue();
    }
}