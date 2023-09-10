package com.shoes.ordering.system.domains.order.adapter.in.controller.rest;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderQuery;
import com.shoes.ordering.system.domains.order.domain.application.dto.track.TrackOrderResponse;
import com.shoes.ordering.system.domains.order.domain.application.ports.input.message.service.OrderQueryService;
import com.shoes.ordering.system.domains.order.domain.core.exception.OrderNotFoundException;
import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = TestConfiguration.class)
@AutoConfigureMockMvc
class OrderQueryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderQueryService orderQueryService;

    @Test
    @DisplayName("정상 Order 조회")
    void trackOrderTest() throws Exception {
        // given
        UUID orderTrackingId = UUID.randomUUID();
        OrderStatus orderStatus = OrderStatus.PENDING;
        List<String> failureMessages = new ArrayList<>();
        TrackOrderResponse expectedResponse = TrackOrderResponse.builder()
                .orderTrackingId(orderTrackingId)
                .orderStatus(orderStatus)
                .failureMessages(failureMessages)
                .build();

        given(orderQueryService.trackOrder(any(TrackOrderQuery.class))).willReturn(expectedResponse);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/orders/{orderTrackingId}", orderTrackingId)
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderTrackingId").value(orderTrackingId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderStatus").value(orderStatus.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.failureMessages").isEmpty());
    }

    @Test
    @DisplayName("정상 Order 조회 에러 확인: OrderNotFound, 404 Error 를 반환한다.")
    void trackOrderWithOrderNotFoundErrorTest() throws Exception {
        // given
        UUID unknownOrderTrackingId = UUID.randomUUID();
        String errorMessage ="Could not find order with tracking id: " + unknownOrderTrackingId;
        given(orderQueryService.trackOrder(any(TrackOrderQuery.class)))
                .willThrow(new OrderNotFoundException(errorMessage));


        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/orders/{orderTrackingId}", unknownOrderTrackingId)
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorMessage));
    }

    @Test
    @DisplayName("정상 Order 조회 에러 확인: 예상하지 못한 에러 발생 시, 500 Error 를 반환한다.")
    void trackOrderWithUnExpectedErrorTest() throws Exception {
        // given
        String orderTrackingId = UUID.randomUUID().toString();
        String errorMessage = "UnExpected Error";
        given(orderQueryService.trackOrder(any(TrackOrderQuery.class)))
                .willAnswer(invocation -> {
                    throw new Exception("UnExpected Error");
                });


        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/orders/{orderTrackingId}", orderTrackingId)
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorMessage));
    }
}