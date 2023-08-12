package com.shoes.ordering.system.domains.order.domain.core.entity;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.order.domain.core.exception.OrderDomainException;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderId;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderStatus;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


class OrderTest {

    private OrderId orderId;
    private MemberId memberId;
    private StreetAddress deliveryAddress;
    private Money orderPrice;
    private Money productPrice;

    private List<OrderItem> items;
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    @BeforeEach
    void setUp() {
        orderId = new OrderId(UUID.randomUUID());
        memberId = new MemberId(UUID.randomUUID());
        deliveryAddress = new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City");
        orderPrice = new Money(new BigDecimal("0.00"));
        items = new ArrayList<OrderItem>();
        trackingId = new TrackingId(UUID.randomUUID());
        orderStatus = OrderStatus.PENDING;
        failureMessages = new ArrayList<String>();
    }

    @Test
    @DisplayName("정상 Order 생성 확인")
    void testInitializeOrder() {
        // given
        items.add(createOrderItem());

        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(orderStatus)
                .failureMessages(failureMessages)
                .build();

        // when
        order.initializeOrder();

        // then
        assertThat(order.getId()).isNotNull();
        assertThat(order.getTrackingId()).isNotNull();

        assertThat(order.getItems()).isNotNull();
        for (OrderItem item : order.getItems()) {
            assertThat(item).isNotNull();
            assertThat(item.getOrderId()).isEqualTo(order.getId());
        }

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PENDING);
    }

    @Test
    @DisplayName("정상 pay 확인")
    void payTest() {
        // given
        items.add(createOrderItem());

        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();

        // when
        order.pay();

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PAID);
    }

    @Test
    @DisplayName("정상 pay 에러 확인1: pay 는 한번만 가능하다.")
    void payErrorTest1() {
        // given
        items.add(createOrderItem());

        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();
        order.pay();

        // when, then
        assertThatThrownBy(order::pay).isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("정상 approve 확인")
    void approveTest() {
        // given
        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();

        order.pay();

        // when
        order.approve();

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.APPROVED);
    }

    @Test
    @DisplayName("정상 approve 에러 확인1: PENDING 상태에서는 approve 가 불가능하다.")
    void approveErrorTest1() {
        // given
        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();

        // when, then
        assertThatThrownBy(order::approve).isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("정상 approve 에러 확인2: CANCELLING 상태에서는 approve 가 불가능하다.")
    void approveErrorTest2() {
        // given
        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();
        order.pay();

        List<String> testFailureMessage = List.of("Test Cancel");
        order.initCancel(testFailureMessage);

        // when, then
        assertThatThrownBy(order::approve).isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("정상 approve 에러 확인3: CANCELLED 상태에서는 approve 가 불가능하다.")
    void approveErrorTest3() {
        // given
        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();

        List<String> testFailureMessage = List.of("Test Cancel");
        order.cancel(testFailureMessage);

        // when, then
        assertThatThrownBy(order::approve).isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("정상 CANCELLING 확인: PAID 상테만 취소 대기 상태를 만들 수 있다.")
    void initCancelTest() {
        // given
        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();

        order.pay();

        // when
        List<String> testFailureMessage = List.of("Test Cancel");
        order.initCancel(testFailureMessage);

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLING);
    }

    @Test
    @DisplayName("정상 CANCELLING 에러 확인1: PENDING 상태는 CANCELLING 상태가 될 수 없다.")
    void initCancelErrorTest1() {
        // given
        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();

        // when, then
        List<String> testFailureMessage = List.of("Test Cancel");

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PENDING);
        assertThatThrownBy(() -> order.initCancel(testFailureMessage)).isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("정상 CANCELLING 에러 확인2: APPROVED 상태는 CANCELLING 상태가 될 수 없다.")
    void initCancelErrorTest2() {
        // given
        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();

        order.pay();
        order.approve();

        // when, then
        List<String> testFailureMessage = List.of("Test Cancel");

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.APPROVED);
        assertThatThrownBy(() -> order.initCancel(testFailureMessage)).isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("정상 cancel 확인1: PENDING 상태에서 정상 cancel 확인")
    void cancelTest1() {
        // given
        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();

        // when
        List<String> testFailureMessage = List.of("Test Cancel");

        order.cancel(testFailureMessage);

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    @DisplayName("정상 cancel 확인1: CANCELLED 상태에서 정상 cancel 확인")
    void cancelTest2() {
        // given
        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();

        order.pay();

        List<String> testInitFailureMessage = List.of("Test InitCancel");
        order.initCancel(testInitFailureMessage);

        // when
        List<String> testFailureMessage = List.of("Test Cancel");

        order.cancel(testFailureMessage);

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
    }

    @Test
    @DisplayName("정상 cancel 에러 확인: APPROVED 상태는 cancel 할 수 없다.")
    void cancelErrorTest() {
        // given
        Order order = Order.builder()
                .id(orderId)
                .memberId(memberId)
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .orderStatus(OrderStatus.PENDING)
                .failureMessages(failureMessages)
                .build();

        order.pay();
        order.approve();

        // when, then
        List<String> testFailureMessage = List.of("Test Cancel");
        assertThatThrownBy(() -> order.cancel(testFailureMessage)).isInstanceOf(OrderDomainException.class);

    }

    private OrderItem createOrderItem() {
        int quantity = 2;
        productPrice = new Money(new BigDecimal("100.00"));
        Product product = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .name("Test name")
                .productCategory(ProductCategory.SHOES)
                .description("Test Description")
                .price(productPrice)
                .build();

        orderPrice.add(productPrice.multiply(quantity));

        return OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .price(productPrice)
                .subTotal(productPrice.multiply(quantity))
                .build();
    }
}