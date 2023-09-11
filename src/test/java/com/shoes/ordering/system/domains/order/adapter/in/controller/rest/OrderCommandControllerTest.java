package com.shoes.ordering.system.domains.order.adapter.in.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderCommand;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderResponse;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.OrderAddress;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.OrderItem;
import com.shoes.ordering.system.domains.order.domain.application.ports.input.message.service.OrderCommandService;
import com.shoes.ordering.system.domains.order.domain.core.exception.OrderDomainException;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderStatus;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = TestConfiguration.class)
@AutoConfigureMockMvc
class OrderCommandControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderCommandService orderCommandService;
    @Autowired
    private ObjectMapper objectMapper;

    private final UUID MEMBER_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41");
    private final UUID PRODUCT_ID = UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb48");
    private BigDecimal totalPrice = new BigDecimal("00.00");

    @Test
    @DisplayName("정상 Order 생성 확인")
    void createOrderTest() throws Exception {
        // given
        CreateOrderCommand createOrderCommand = createCreateOrderCommand(2, new BigDecimal("50.00"));

        UUID orderTrackingId = UUID.randomUUID();
        OrderStatus orderStatus = OrderStatus.PENDING;
        String message = "Order created successfully";

        CreateOrderResponse expectedResponse = CreateOrderResponse.builder()
                .orderTrackingId(orderTrackingId)
                .orderStatus(orderStatus)
                .message(message)
                .build();

        given(orderCommandService.createOrder(any(CreateOrderCommand.class))).willReturn(expectedResponse);

        // Convert the createProductCommand to JSON
        String content = asJsonString(createOrderCommand);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/orders")
                        .header("Content-Type", "application/json")
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderTrackingId").value(orderTrackingId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderStatus").value(orderStatus.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message));

    }

    @Test
    @DisplayName("정상 Order 생성 에러 확인: 입력 값이 잘못된 경우, 400 Error 를 반환한다.")
    void createOrderErrorTest() throws Exception {
        // given
        CreateOrderCommand createOrderCommand = createCreateOrderCommand(2, new BigDecimal("50.00"));

        given(orderCommandService.createOrder(any(CreateOrderCommand.class)))
                .willThrow(new ConstraintViolationException("Validation failed", new HashSet<>()));


        // Convert the createProductCommand to JSON
        String content = asJsonString(createOrderCommand);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/orders")
                        .header("Content-Type", "application/json")
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("정상 Order 생성 에러 확인: 예상하지 못한 에러 발생 시, 500 Error 를 반환한다.")
    void createOrderError2Test() throws Exception {
        // given
        given(orderCommandService.createOrder(any(CreateOrderCommand.class)))
                .willAnswer(invocation -> {
                    throw new Exception("UnExpected Error");
                });

        // Convert the createProductCommand to JSON
        String content = asJsonString(null);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/orders")
                        .header("Content-Type", "application/json")
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    @DisplayName("정상 Limited Order 생성 확인")
    void createLimitedOrderTest() throws Exception {
        // given
        CreateOrderCommand createOrderCommand = createCreateOrderCommand(2, new BigDecimal("50.00"));

        UUID orderTrackingId = UUID.randomUUID();
        OrderStatus orderStatus = OrderStatus.PENDING;
        String message = "Order created successfully";

        CreateOrderResponse expectedResponse = CreateOrderResponse.builder()
                .orderTrackingId(orderTrackingId)
                .orderStatus(orderStatus)
                .message(message)
                .build();

        given(orderCommandService.createLimitedOrder(any(CreateOrderCommand.class)))
                .willReturn(expectedResponse)
                .willThrow(OrderDomainException.class);

        // Convert the createProductCommand to JSON
        String content = asJsonString(createOrderCommand);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/orders/limited")
                        .header("Content-Type", "application/json")
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderTrackingId").value(orderTrackingId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderStatus").value(orderStatus.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(message));

    }

    @Test
    @DisplayName("정상 Limited Order 동시성 테스트: 제한 주문 시, 상품에 대한 계정 당 1회 주문 제한")
    void createLimitedOrderTest_MultiMember() throws Exception {
        // given
        CreateOrderCommand createOrderCommand = createCreateOrderCommand(2, new BigDecimal("50.00"));

        UUID orderTrackingId = UUID.randomUUID();
        OrderStatus orderStatus = OrderStatus.PENDING;
        String message = "Order created successfully";

        CreateOrderResponse expectedResponse = CreateOrderResponse.builder()
                .orderTrackingId(orderTrackingId)
                .orderStatus(orderStatus)
                .message(message)
                .build();

        // Convert the createProductCommand to JSON
        String content = asJsonString(createOrderCommand);

        given(orderCommandService.createLimitedOrder(any(CreateOrderCommand.class)))
                .willReturn(expectedResponse)
                .willThrow(new OrderDomainException("Limited Order can create only once"));

        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        AtomicInteger successfulCalls = new AtomicInteger(0);
        AtomicInteger failCalls = new AtomicInteger(0);

        AtomicBoolean isFirstRequest = new AtomicBoolean(true);

        // when and then
        for(int i = 0; i < threadCount; i ++) {
            executorService.submit(() -> {
                try {
                    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                                    .post("/orders/limited")
                                    .header("Content-Type", "application/json")
                                    .content(content))
                            .andReturn();

                    if (isFirstRequest.getAndSet(false)) {
                        successfulCalls.incrementAndGet();

                        // 첫 번째 호출에 대한 검증
                        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
                        assertThat(result.getResponse().getContentAsString()).contains(orderTrackingId.toString());
                        assertThat(result.getResponse().getContentAsString()).contains(orderStatus.toString());
                        assertThat(result.getResponse().getContentAsString()).contains(message);
                    } else {
                        // 그 이후 호출에 대한 검증 (400 BAD_REQUEST)
                        failCalls.incrementAndGet();
                        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
                    }

                } catch (Exception e) {
                    assertThat(e).isInstanceOf(OrderDomainException.class);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        assertThat(successfulCalls.get()).isEqualTo(1);
        assertThat(failCalls.get()).isEqualTo(threadCount - 1);

    }


    private CreateOrderCommand createCreateOrderCommand(int quantity, BigDecimal productPrice) {
        List<OrderItem> items = new ArrayList<>();
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

    // 객체를 JSON String 으로 변환
    private String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}