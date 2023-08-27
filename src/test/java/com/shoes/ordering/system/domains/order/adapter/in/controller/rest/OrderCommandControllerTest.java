package com.shoes.ordering.system.domains.order.adapter.in.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderCommand;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.CreateOrderResponse;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.OrderAddress;
import com.shoes.ordering.system.domains.order.domain.application.dto.create.OrderItem;
import com.shoes.ordering.system.domains.order.domain.application.ports.input.message.service.OrderCommandService;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderStatus;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.*;

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