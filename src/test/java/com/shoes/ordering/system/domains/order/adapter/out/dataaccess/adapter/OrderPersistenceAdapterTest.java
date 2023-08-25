package com.shoes.ordering.system.domains.order.adapter.out.dataaccess.adapter;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.entity.OrderEntity;
import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.mapper.OrderDataAccessMapper;
import com.shoes.ordering.system.domains.order.adapter.out.dataaccess.repository.OrderJpaRepository;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.entity.OrderItem;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderStatus;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.TrackingId;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.mapper.ProductDataAccessMapper;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductJpaRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.shoes.ordering.system.TestConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest(classes = TestConfiguration.class)
class OrderPersistenceAdapterTest {

    @Autowired
    OrderPersistenceAdapter orderPersistenceAdapter;
    @Autowired
    OrderJpaRepository orderJpaRepository;
    @Autowired
    ProductDataAccessMapper productDataAccessMapper;
    @Autowired
    ProductJpaRepository productJpaRepository;
    @Autowired
    OrderDataAccessMapper orderDataAccessMapper;
    private BigDecimal totalPrice = new BigDecimal("00.00");

    @AfterEach
    void clean() {
        orderJpaRepository.deleteAll();
        productJpaRepository.deleteAll();
        totalPrice = new BigDecimal("00.00");
    }

    @Test
    @DisplayName("정상 OrderEntity save 테스트")
    void saveTest() {
        // given
        int productQuantity = 1;
        BigDecimal productPrice = new BigDecimal("200.00");
        OrderItem orderItem = createOrderItem(productQuantity, productPrice);

        Order order = createOrder(totalPrice, List.of(orderItem));
        order.initializeOrder();

        // when
        orderPersistenceAdapter.save(order);

        // then
        List<OrderEntity> results = orderJpaRepository.findAll();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getId()).isNotNull();
        assertThat(results.get(0).getId()).isEqualTo(order.getId().getValue());
    }

    @Test
    @DisplayName("정상 findByTrackingId 확인")
    void findByTrackingIdTest() {
        // given
        int productQuantity = 1;
        BigDecimal productPrice = new BigDecimal("200.00");
        OrderItem orderItem = createOrderItem(productQuantity, productPrice);

        Order order = createOrder(totalPrice, List.of(orderItem));
        order.initializeOrder();

        OrderEntity orderEntity = orderDataAccessMapper.orderToOrderEntity(order);

        orderJpaRepository.save(orderEntity);

        // when
        Optional<Order> result = orderPersistenceAdapter.findByTrackingId(orderEntity.getTrackingId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getPrice().getAmount()).isEqualTo(productPrice);
    }

    @Test
    @DisplayName("정상 findByTrackingId 에러 확인")
    void findByTrackingIdErrorTest() {
        // given
        UUID unknownOrderTrackingId = UUID.randomUUID();

        // when
        Optional<Order> result = orderPersistenceAdapter.findByTrackingId(unknownOrderTrackingId);

        // then
        assertThat(result).isEmpty();
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
        Product product = createProduct(productPrice);

        totalPrice = totalPrice.add(productPrice.multiply(BigDecimal.valueOf(quantity)));

        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .price(new Money(productPrice))
                .subTotal(new Money(productPrice.multiply(BigDecimal.valueOf(quantity))))
                .build();
    }

    private Product createProduct(BigDecimal productPrice) {
        Product product = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .name("Test name")
                .productCategory(ProductCategory.SHOES)
                .description("Test Description")
                .price(new Money(productPrice))
                .build();

        productJpaRepository.save(productDataAccessMapper.productToProductEntity(product));

        return product;
    }

}