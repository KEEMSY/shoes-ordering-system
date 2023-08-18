package com.shoes.ordering.system.domains.order.domain.application.dto.track;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TrackOrderQueryTest {

    @Test
    @DisplayName("TrackOrderQuery 생성 확인")
    void trackOrderQueryTest() {
        // given
        UUID orderTrackingId = UUID.randomUUID();

        TrackOrderQuery.Builder builder = TrackOrderQuery.builder()
                .orderTrackingId(orderTrackingId);

        // when
        TrackOrderQuery trackOrderQuery = builder.build();

        // then
        assertThat(trackOrderQuery.getOrderTrackingId()).isEqualTo(orderTrackingId);
    }

    @Test
    @DisplayName("TrackOrderQuery 에러 확인: 프로퍼티에는 null 이 올 수 없다.")
    void trackOrderQueryErrorTest() {
        // given
        UUID orderTrackingId = null;

        // when, then
        assertThatThrownBy(() -> TrackOrderQuery.builder()
                .orderTrackingId(orderTrackingId)
                .build())
                .isInstanceOf(ConstraintViolationException.class);
    }
}
