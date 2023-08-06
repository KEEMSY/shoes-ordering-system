package com.shoes.ordering.system.domains.product.domain.application.dto.track;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TrackProductQueryTest {

    @Test
    @DisplayName("정상 TrackProductQuery 생성 확인")
    public void TrackProductQueryTest1() {
        // given
        UUID targetProductId = UUID.randomUUID();

        // when
        TrackProductQuery trackProductQuery = TrackProductQuery.builder()
                .productId(targetProductId)
                .build();

        // then
        assertThat(trackProductQuery).isNotNull();
        assertThat(trackProductQuery.getProductId()).isEqualTo(targetProductId);
    }

    @Test
    @DisplayName("비정상 TrackProductQuery 생성 불가 확인: 프로퍼티에는 null 이 올수 없다.")
    public void TrackProductQueryTest2() {
        // given
        UUID targetProductId = UUID.randomUUID();

        // when, then
        assertThatThrownBy(() -> TrackProductQuery.builder()
                .productId(null)
                .build()).isInstanceOf(ConstraintViolationException.class);
    }
}
