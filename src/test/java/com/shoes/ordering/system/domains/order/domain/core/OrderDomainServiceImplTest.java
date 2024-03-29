package com.shoes.ordering.system.domains.order.domain.core;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.entity.OrderItem;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.TrackingId;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCancelledEvent;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCreatedEvent;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderPaidEvent;
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

    private Order createOrder() {
        return Order.builder()
                .deliveryAddress(deliveryAddress)
                .price(orderPrice)
                .items(items)
                .trackingId(trackingId)
                .failureMessages(failureMessages)
                .build();
    }

    @Test
    @DisplayName("정상 OrderCreatedEvent 생성 확인")
    void validateAndInitiateOrderTest() {
        // given
        Order order = createOrder();

        // when
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order);

        // then
        assertThat(orderCreatedEvent).isNotNull();
        assertThat(orderCreatedEvent.getOrder()).isEqualTo(order);
        assertThat(orderCreatedEvent.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("정상 OrderPaidEvent 생성 확인")
    void payOrderTest() {
        // given
        Order order = createOrder();

        Order createdOrder = orderDomainService.validateAndInitiateOrder(order).getOrder();

        // when
        OrderPaidEvent orderPaidEvent = orderDomainService.payOrder(createdOrder);

        // then
        assertThat(orderPaidEvent).isNotNull();
        assertThat(orderPaidEvent.getOrder()).isEqualTo(order);
        assertThat(orderPaidEvent.getOrder().getOrderStatus()).isEqualTo(OrderStatus.PAID);
        assertThat(orderPaidEvent.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("정상 Order approve 확인")
    void approveOrderTest() {
        // given
        Order order = createOrder();

        Order createdOrder = orderDomainService.validateAndInitiateOrder(order).getOrder();
        Order paidOrder = orderDomainService.payOrder(createdOrder).getOrder();

        // when
        orderDomainService.approveOrder(paidOrder);

        // then
        assertThat(paidOrder.getOrderStatus()).isEqualTo(OrderStatus.APPROVED);
    }

    @Test
    @DisplayName("정상 cancelOrder 확인")
    void cancelOrderPaymentTest() {
        // given
        Order order = createOrder();

        Order createdOrder = orderDomainService.validateAndInitiateOrder(order).getOrder();
        Order paidOrder = orderDomainService.payOrder(createdOrder).getOrder();

        List<String> cancelFailureMessages = new ArrayList<>();
        cancelFailureMessages.add("Test Cancelling Order");

        // when
        OrderCancelledEvent orderCancelledEvent = orderDomainService.cancelOrderPayment(paidOrder, cancelFailureMessages);

        // then
        assertThat(orderCancelledEvent).isNotNull();
        assertThat(orderCancelledEvent.getOrder()).isEqualTo(paidOrder);
        assertThat(orderCancelledEvent.getCreatedAt()).isNotNull();
        assertThat(paidOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCELLING);
        assertThat(paidOrder.getFailureMessages().size()).isEqualTo(1);
        assertThat(paidOrder.getFailureMessages().get(0)).isEqualTo("Test Cancelling Order");
    }

    @Test
    @DisplayName("정상 cancelOrder 확인1: PENDING 상태")
    void cancelOrderTest1() {
        // given
        Order order = createOrder();

        Order createdOrder = orderDomainService.validateAndInitiateOrder(order).getOrder();

        List<String> cancelFailureMessages = new ArrayList<>();
        cancelFailureMessages.add("Test Cancelled Order");

        // when
        orderDomainService.cancelOrder(createdOrder, cancelFailureMessages);

        // then
        assertThat(createdOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(createdOrder.getFailureMessages().get(0)).isEqualTo("Test Cancelled Order");
    }

    @Test
    @DisplayName("정상 cancelOrder 확인1: CANCELLING 상태")
    void cancelOrderTest2() {
        // given
        Order order = createOrder();

        Order createdOrder = orderDomainService.validateAndInitiateOrder(order).getOrder();
        Order paidOrder = orderDomainService.payOrder(createdOrder).getOrder();

        List<String> cancelFailureMessages = new ArrayList<>();
        cancelFailureMessages.add("Test Cancelled Order");
        orderDomainService.cancelOrderPayment(paidOrder, cancelFailureMessages);

        // when
        orderDomainService.cancelOrder(createdOrder, cancelFailureMessages);

        // then
        assertThat(createdOrder.getOrderStatus()).isEqualTo(OrderStatus.CANCELLED);
        assertThat(createdOrder.getFailureMessages().get(0)).isEqualTo("Test Cancelled Order");
    }
}