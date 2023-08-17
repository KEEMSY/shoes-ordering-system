package com.shoes.ordering.system.domains.order.domain.application.helper;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberStatus;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderCommand;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.OrderAddress;
import com.shoes.ordering.system.domains.order.domain.application.mapper.OrderDataMapper;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.repository.OrderRepository;
import com.shoes.ordering.system.domains.order.domain.core.OrderDomainService;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.entity.OrderItem;
import com.shoes.ordering.system.domains.order.domain.core.event.OrderCreatedEvent;
import com.shoes.ordering.system.domains.order.domain.core.exception.OrderDomainException;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderId;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderStatus;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
class OrderCreateHelperTest {

    @Autowired
    private OrderCreateHelper orderCreateHelper;
    @Autowired
    private OrderDataMapper orderDataMapper;
    @MockBean
    private OrderDomainService orderDomainService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;


    private final UUID MEMBER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41");
    private final UUID PRODUCT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48");
    private BigDecimal totalPrice = new BigDecimal("00.00");


    @Test
    @DisplayName("정상 persistOrder 테스트")
    void persistOrderTest() {
        // given
        CreateOrderCommand createOrderCommand = createCreateOrderCommand(2, new BigDecimal("50.00"));

        Order targetOrder = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        targetOrder.setId(new OrderId(UUID.randomUUID()));

        OrderCreatedEvent orderCreatedEvent = createMockOrderCreatedEvent(targetOrder);

        Member member = createMember();

        when(memberRepository.findByMemberId(any(UUID.class))).thenReturn(Optional.of(member));
        when(orderDomainService.validateAndInitiateOrder(any(Order.class))).thenReturn(orderCreatedEvent);
        when(orderRepository.save(any(Order.class))).thenReturn(targetOrder);

        // when
        OrderCreatedEvent resultOrderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);

        // then
        assertThat(resultOrderCreatedEvent).isNotNull();
        assertThat(targetOrder.getId().getValue()).isEqualTo(resultOrderCreatedEvent.getOrder().getId().getValue());
        assertThat(targetOrder.getOrderStatus()).isEqualTo(resultOrderCreatedEvent.getOrder().getOrderStatus());

    }


    @Test
    @DisplayName("정상 persistOrder 에러 테스트: Member 가 존재하지 않을 경우")
    void persistOrder_MemberNotFoundTest() {
        // given
        CreateOrderCommand createOrderCommand = createCreateOrderCommand(2, new BigDecimal("50.00"));
        when(memberRepository.findByMemberId(any(UUID.class))).thenReturn(Optional.ofNullable(null));

        // when, then
        assertThatThrownBy(() -> orderCreateHelper.persistOrder(createOrderCommand))
                .isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("정상 persistOrder 에러 테스트: order 저장이 실패 할 경우")
    void persistOrder_SaveFailedTest() {
        // given
        CreateOrderCommand createOrderCommand = createCreateOrderCommand(2, new BigDecimal("50.00"));

        Order targetOrder = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        targetOrder.setId(new OrderId(UUID.randomUUID()));

        OrderCreatedEvent orderCreatedEvent = createMockOrderCreatedEvent(targetOrder);
        when(orderDomainService.validateAndInitiateOrder(any(Order.class))).thenReturn(orderCreatedEvent);

        Member member = createMember();
        when(memberRepository.findByMemberId(any(UUID.class))).thenReturn(Optional.of(member));

        // when, then
        when(orderRepository.save(any(Order.class))).thenReturn(null);

        assertThatThrownBy(() -> orderCreateHelper.persistOrder(createOrderCommand))
                .isInstanceOf(OrderDomainException.class);
    }


    private CreateOrderCommand createCreateOrderCommand(int quantity, BigDecimal productPrice) {
        List<com.shoes.ordering.system.domains.order.domain.application.dto.create.OrderItem> items = new ArrayList<>();
        OrderAddress orderAddress = new OrderAddress("123 Street", "99999", "City");

        items.add(createOrderItem(quantity, productPrice));

        totalPrice = totalPrice.add(productPrice.multiply(BigDecimal.valueOf(quantity)));

        return CreateOrderCommand.builder()
                .memberId(MEMBER_ID)
                .price(totalPrice)
                .items(items)
                .address(orderAddress)
                .build();
    }

    private com.shoes.ordering.system.domains.order.domain.application.dto.create.OrderItem
    createOrderItem(int quantity, BigDecimal productPrice) {

        String productName = "Test Product Name";
        ProductCategory productCategory = ProductCategory.SHOES;
        String description = "Test Description";

        return com.shoes.ordering.system.domains.order.domain.application.dto.create.OrderItem.builder()
                .productId(PRODUCT_ID)
                .name(productName)
                .productCategory(productCategory)
                .description(description)
                .quantity(quantity)
                .price(productPrice)
                .subTotal(productPrice.multiply(BigDecimal.valueOf(quantity)))
                .build();
    }

    private OrderCreatedEvent createMockOrderCreatedEvent(Order order) {
        return new OrderCreatedEvent(order, ZonedDateTime.now());
    }

    private Member createMember() {
        return Member.builder()
                .memberId(new MemberId(MEMBER_ID))
                .memberStatus(MemberStatus.ACTIVATE)
                .name("KEEMSY")
                .password("Password123!")
                .email("keemsy@example.com")
                .memberKind(MemberKind.CUSTOMER)
                .phoneNumber("qwe123qwe!R")
                .address(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .build();

    }

    private Order createOrder(Money price, List<OrderItem> items) {
        return Order.builder()
                .memberId(new MemberId(MEMBER_ID))
                .deliveryAddress(new StreetAddress(UUID.randomUUID(), "123 Street", "99999", "City"))
                .price(new Money(totalPrice))
                .items(items)
                .orderStatus(OrderStatus.PENDING)

                .build();
    }
}