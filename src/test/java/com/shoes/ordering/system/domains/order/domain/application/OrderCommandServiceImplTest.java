package com.shoes.ordering.system.domains.order.domain.application;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.StreetAddress;
import com.shoes.ordering.system.domains.member.domain.application.ports.output.repository.MemberRepository;
import com.shoes.ordering.system.domains.member.domain.core.entity.Member;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberId;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberKind;
import com.shoes.ordering.system.domains.member.domain.core.valueobject.MemberStatus;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderCommand;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderResponse;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.OrderAddress;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.OrderItem;
import com.shoes.ordering.system.domains.order.domain.application.mapper.OrderDataMapper;
import com.shoes.ordering.system.domains.order.domain.application.ports.input.message.service.OrderCommandService;
import com.shoes.ordering.system.domains.order.domain.application.ports.output.repository.OrderRepository;
import com.shoes.ordering.system.domains.order.domain.core.entity.Order;
import com.shoes.ordering.system.domains.order.domain.core.exception.OrderDomainException;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderId;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderStatus;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductAppliedRedisRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
class OrderCommandServiceImplTest {
    @Autowired
    private OrderCommandService orderCommandService;
    @Autowired
    private OrderDataMapper orderDataMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductAppliedRedisRepository productAppliedRedisRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;

    private final UUID PRODUCT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48");
    private final UUID ORDER_ID = UUID.fromString("15a497c1-0f4b-4eff-b9f4-c402c8c07afb");
    private final UUID MEMBER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @Test
    @DisplayName("정상 createOrder 테스트")
    void createOrderTest() {
        OrderItem orderItem1 = createOrderItem(1, new BigDecimal("50.00"));
        OrderItem orderItem2 = createOrderItem(3, new BigDecimal("50.00"));

        createOrderCommand = CreateOrderCommand.builder()
                .memberId(MEMBER_ID)
                .address(OrderAddress.builder()
                        .street("123 Street")
                        .postalCode("99999")
                        .city("City")
                        .build())
                .price(PRICE)
                .items(List.of(orderItem1, orderItem2)
                )
                .build();

        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));

        Member member = createMember();

        when(memberRepository.findByMemberId(MEMBER_ID)).thenReturn(Optional.of(member));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // when
        CreateOrderResponse resultCreateOrderResponse = orderCommandService.createOrder(createOrderCommand);

        // then
        assertThat(resultCreateOrderResponse.getOrderStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(resultCreateOrderResponse.getMessage()).isEqualTo("Order created successfully");
        assertThat(resultCreateOrderResponse.getOrderTrackingId()).isNotNull();
    }

    @Test
    @DisplayName("정상 createOrder 에러 확인 테스트: Total price 와 OrderItem price 가 일치하지 않는 경우")
    void createOrderWithWrongTotalPrice() {
        // given
        OrderItem orderItem1 = createOrderItem(1, new BigDecimal("50.00"));
        OrderItem orderItem2 = createOrderItem(3, new BigDecimal("50.00"));

        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .memberId(MEMBER_ID)
                .address(OrderAddress.builder()
                        .street("123 Street")
                        .postalCode("99999")
                        .city("City")
                        .build())
                .price(new BigDecimal("250.00")) // wrong price
                .items(List.of(orderItem1, orderItem2))
                .build();

        // when, then
        assertThatThrownBy(() -> orderCommandService.createOrder(createOrderCommandWrongPrice))
                .isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("정상 createOrder 에러 확인 테스트: Product price 가 잘못된 경우")
    void createOrderWithWrongProductPrice() {
        // given
        OrderItem orderItem = createOrderItem(1, new BigDecimal("60.00"));
        OrderItem orderItemWithWrongProductPrice = OrderItem.builder()
                .productId(PRODUCT_ID)
                .quantity(2)
                .price(new BigDecimal("120.00"))
                .subTotal(new BigDecimal("60.00")) // wrong subTotal
                .build();

        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .memberId(MEMBER_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .postalCode("1000AB")
                        .city("Paris")
                        .build())
                .price(new BigDecimal("300.00"))
                .items(List.of(orderItem, orderItemWithWrongProductPrice))
                .build();

        // when, then
        assertThatThrownBy(() -> orderCommandService.createOrder(createOrderCommandWrongProductPrice))
                .isInstanceOf(OrderDomainException.class);
    }

    @Test
    @DisplayName("정상 createLimitedOrder 테스트")
    void createLimitedOrderTest() {
        OrderItem orderItem1 = createOrderItem(1, new BigDecimal("50.00"));
        OrderItem orderItem2 = createOrderItem(3, new BigDecimal("50.00"));

        createOrderCommand = CreateOrderCommand.builder()
                .memberId(MEMBER_ID)
                .address(OrderAddress.builder()
                        .street("123 Street")
                        .postalCode("99999")
                        .city("City")
                        .build())
                .price(PRICE)
                .items(List.of(orderItem1, orderItem2)
                )
                .build();


        Order order = createOrder(createOrderCommand);
        Member member = createMember();

        when(memberRepository.findByMemberId(MEMBER_ID)).thenReturn(Optional.of(member));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productAppliedRedisRepository.addMemberIdToLimitedProduct(any(UUID.class), any(UUID.class)))
                .thenReturn(true)
                .thenReturn(false);

        // when
        CreateOrderResponse resultCreateOrderResponse = orderCommandService.createLimitedOrder(createOrderCommand);

        // then
        assertThat(resultCreateOrderResponse.getOrderStatus()).isEqualTo(OrderStatus.PENDING);
        assertThat(resultCreateOrderResponse.getMessage()).isEqualTo("Order created successfully");
        assertThat(resultCreateOrderResponse.getOrderTrackingId()).isNotNull();
    }

    @Test
    @DisplayName("정상 createLimitedOrder 동시성 테스트: 하나의 ProductId 에 대하여 한번만 등록 가능하다.")
    void createLimitedOrderTest_MultiMember() throws InterruptedException {
        OrderItem orderItem1 = createOrderItem(1, new BigDecimal("50.00"));
        OrderItem orderItem2 = createOrderItem(3, new BigDecimal("50.00"));

        createOrderCommand = CreateOrderCommand.builder()
                .memberId(MEMBER_ID)
                .address(OrderAddress.builder()
                        .street("123 Street")
                        .postalCode("99999")
                        .city("City")
                        .build())
                .price(PRICE)
                .items(List.of(orderItem1, orderItem2)
                )
                .build();


        Order order = createOrder(createOrderCommand);
        Member member = createMember();

        when(memberRepository.findByMemberId(MEMBER_ID)).thenReturn(Optional.of(member));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(productAppliedRedisRepository.addMemberIdToLimitedProduct(any(UUID.class), any(UUID.class)))
                .thenReturn(true)
                .thenReturn(false);

        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successfulCalls = new AtomicInteger(0);

        // when
        for(int i = 0; i < threadCount; i ++) {
            executorService.submit(() -> {
                try {
                    CreateOrderResponse resultCreateOrderResponse = orderCommandService.createLimitedOrder(createOrderCommand);
                    successfulCalls.incrementAndGet();

                    assertThat(resultCreateOrderResponse.getOrderStatus()).isEqualTo(OrderStatus.PENDING);
                    assertThat(resultCreateOrderResponse.getMessage()).isEqualTo("Order created successfully");
                    assertThat(resultCreateOrderResponse.getOrderTrackingId()).isNotNull();

                } catch (Exception e) {
                  assertThat(e).isInstanceOf(OrderDomainException.class);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        // then
        assertThat(successfulCalls.intValue()).isEqualTo(1);
    }

    private Order createOrder(CreateOrderCommand createOrderCommand) {
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.initializeOrder();
        return order;
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

    private OrderItem createOrderItem(int quantity, BigDecimal price) {
        return OrderItem.builder()
                .productId(PRODUCT_ID)
                .quantity(quantity)
                .price(price)
                .subTotal(price.multiply(BigDecimal.valueOf(quantity)))
                .build();
    }
}