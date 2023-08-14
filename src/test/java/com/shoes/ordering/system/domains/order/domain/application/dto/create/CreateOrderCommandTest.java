package com.shoes.ordering.system.domains.order.domain.application.dto.create;


import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.order.domain.core.entity.OrderItem;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CreateOrderCommandTest {

    @Test
    @DisplayName("정상 CreateOrderCommand 생성 확인")
    void createOrderCommandTest() {
        // given
        UUID memberId = UUID.randomUUID();
        BigDecimal price = new BigDecimal("200.00");
        List<OrderItem> items = new ArrayList<>();
        OrderAddress orderAddress = new OrderAddress("123 Street", "99999", "City");

        items.add(createOrderItem());

        CreateOrderCommand.Builder builder = CreateOrderCommand.builder()
                    .memberId(memberId)
                    .price(price)
                    .items(items)
                    .address(orderAddress);

        // when
        CreateOrderCommand createOrderCommand = builder.build();

        // then
        assertThat(createOrderCommand.getMemberId()).isEqualTo(memberId);
        assertThat(createOrderCommand.getPrice()).isEqualTo(price);
        assertThat(createOrderCommand.getItems()).isEqualTo(items);

        assertThat(createOrderCommand.getAddress()).isNotNull();
    }

    @Test
    @DisplayName("정상 CreateOrderCommand 에러 확인: 프로퍼티에는 null 이 올 수 없다.")
    void createOrderCommandErrorTest() {
        // given
        UUID memberId = UUID.randomUUID();
        List<OrderItem> items = new ArrayList<>();
        OrderAddress orderAddress = new OrderAddress("123 Street", "99999", "City");

        items.add(createOrderItem());

        // when, then
        assertThatThrownBy(() -> CreateOrderCommand.builder()
                .memberId(memberId)
                .price(null)
                .items(items)
                .address(orderAddress)
                .build())
                .isInstanceOf(ConstraintViolationException.class);
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

        Money orderPrice = new Money(new BigDecimal("00.00"));
        orderPrice = orderPrice.add(productPrice.multiply(quantity));

        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .price(productPrice)
                .subTotal(productPrice.multiply(quantity))
                .build();
    }

}