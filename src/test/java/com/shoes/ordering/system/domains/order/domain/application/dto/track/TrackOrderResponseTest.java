package com.shoes.ordering.system.domains.order.domain.application.dto.track;

import com.shoes.ordering.system.domains.order.domain.core.valueobject.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TrackOrderResponseTest {

    @Test
    @DisplayName("TrackOrderResponse 생성 확인")
    void trackOrderResponseTest() {
        // given
        UUID orderTrackingId = UUID.randomUUID();
        OrderStatus orderStatus = OrderStatus.PENDING;
        List<String> failureMessages = new ArrayList<>();

        TrackOrderResponse.Builder builder = TrackOrderResponse.builder()
                .orderTrackingId(orderTrackingId)
                .orderStatus(orderStatus)
                .failureMessages(failureMessages);

        // when
        TrackOrderResponse trackOrderResponse = builder.build();

        // then
        assertThat(trackOrderResponse.getOrderTrackingId()).isEqualTo(orderTrackingId);
        assertThat(trackOrderResponse.getOrderStatus()).isEqualTo(orderStatus);
        assertThat(trackOrderResponse.getFailureMessages()).isEqualTo(failureMessages);
    }

    @Test
    @DisplayName("TrackOrderResponse 에러 확인: 프로퍼티에는 null 이 올 수 없다.")
    void trackOrderResponseErrorTest() {
        // given
        UUID orderTrackingId = UUID.randomUUID();

        // when, then
        assertThatThrownBy(() -> TrackOrderResponse.builder()
                .orderTrackingId(orderTrackingId)
                .orderStatus(null)
                .failureMessages(null)
                .build())
                .isInstanceOf(ConstraintViolationException.class);
    }
}
