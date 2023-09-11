package com.shoes.ordering.system.domains.order.domain.application.dto.create;

import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CreateOrderResponseTest {

    @Test
    @DisplayName("CreateOrderResponse 생성 확인")
    void createOrderResponseTest() {
        // given
        UUID orderTrackingId = UUID.randomUUID();
        OrderStatus orderStatus = OrderStatus.PENDING;
        String message = "Order created successfully";

        CreateOrderResponse.Builder builder = CreateOrderResponse.builder()
                .orderTrackingId(orderTrackingId)
                .orderStatus(orderStatus)
                .message(message);

        // when
        CreateOrderResponse createOrderResponse = builder.build();

        // then
        assertThat(createOrderResponse.getOrderTrackingId()).isEqualTo(orderTrackingId);
        assertThat(createOrderResponse.getOrderStatus()).isEqualTo(orderStatus);
        assertThat(createOrderResponse.getMessage()).isEqualTo(message);
    }

    @Test
    @DisplayName("CreateOrderResponse 에러 확인: 프로퍼티에는 null 이 올 수 없다.")
    void createOrderResponseErrorTest() {
        // given
        UUID orderTrackingId = UUID.randomUUID();

        // when, then
        assertThatThrownBy(() -> CreateOrderResponse.builder()
                .orderTrackingId(orderTrackingId)
                .orderStatus(null)
                .message("Properties can't be null")
                .build())
                .isInstanceOf(ConstraintViolationException.class);
    }
}
