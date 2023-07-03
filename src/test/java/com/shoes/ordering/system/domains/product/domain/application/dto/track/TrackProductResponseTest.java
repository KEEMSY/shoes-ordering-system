package com.shoes.ordering.system.domains.product.domain.application.dto.track;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TrackProductResponseTest {

    @Test
    @DisplayName("정상 TrackProductResponse 생성 확인")
    public void trackProductResponseTest1() {
        // given
        UUID productId = UUID.randomUUID();
        String testProductName = "Test ProductName";
        ProductCategory testProductCategory = ProductCategory.SHOES;
        String testProductDescription = "Test Product Description";
        Money testProductPrice = new Money(new BigDecimal("200.00"));
        List<String> testProductImages = List.of("testURL1", "testURL2");

        // when
        TrackProductResponse trackProductResponse = TrackProductResponse.builder()
                .productId(productId)
                .name(testProductName)
                .productCategory(testProductCategory)
                .description(testProductDescription)
                .price(testProductPrice)
                .productImages(testProductImages)
                .build();

        // then
        assertThat(trackProductResponse).isNotNull();
        assertThat(trackProductResponse.getProductId()).isEqualTo(productId);
        assertThat(trackProductResponse.getDescription()).isEqualTo(testProductDescription);

    }

    @Test
    @DisplayName("비정상 TrackProductResponse 생성 불가 확인")
    public void trackProductResponseTest2() {
        // given
        UUID productId = UUID.randomUUID();
        String testProductName = "Test ProductName";
        ProductCategory testProductCategory = ProductCategory.SHOES;
        String testProductDescription = "Test Product Description";
        Money testProductPrice = new Money(new BigDecimal("200.00"));
        List<String> testProductImages = List.of("testURL1", "testURL2");

        // when, then
        assertThatThrownBy(() -> TrackProductResponse.builder()
                .productId(null)
                .name(testProductName)
                .productCategory(testProductCategory)
                .description(testProductDescription)
                .price(testProductPrice)
                .productImages(testProductImages)
                .build()).isInstanceOf(ConstraintViolationException.class);
    }
}
