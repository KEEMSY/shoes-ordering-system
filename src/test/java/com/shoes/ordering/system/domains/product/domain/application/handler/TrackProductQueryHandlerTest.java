package com.shoes.ordering.system.domains.product.domain.application.handler;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.entity.ProductImage;
import com.shoes.ordering.system.domains.product.domain.core.exception.ProductNotFoundException;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductImageId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class TrackProductQueryHandlerTest {

    @Autowired
    private TrackProductQueryHandler trackProductQueryHandler;
    @Autowired
    private ProductRepository productRepository;

    private TrackProductQuery trackProductQuery;
    private Product product;

    @BeforeEach
    public void init() {
        product = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .name("Test name")
                .productCategory(ProductCategory.SHOES)
                .description("Test Product Description")
                .price(new Money(new BigDecimal("200.00")))
                .productImages(List.of(new ProductImage(new ProductImageId(UUID.randomUUID()), "TestUrl")))
                .build();

        when(productRepository.findByProductId(product.getId().getValue())).thenReturn(Optional.of(product));
    }

    @Test
    @DisplayName("정상 TrackProductResponse 확인")
    public void trackProductTest1() {
        // given
        trackProductQuery = TrackProductQuery.builder()
                .productId(product.getId().getValue())
                .build();

        // when
        TrackProductResponse resultTrackProductResponse = trackProductQueryHandler.trackProduct(trackProductQuery);

        // then
        assertThat(resultTrackProductResponse).isNotNull();
        assertThat(resultTrackProductResponse.getProductId()).isEqualTo(product.getId().getValue());
    }

    @Test
    @DisplayName("비정상 TrackProductResponse 확인: 존재하지 않는 Product 조회")
    public void trackProductTest2() {
        // given
        UUID unknownProductId = UUID.randomUUID();

        // when
        trackProductQuery = TrackProductQuery.builder()
                .productId(unknownProductId)
                .build();

        // then
        assertThatThrownBy(() -> trackProductQueryHandler.trackProduct(trackProductQuery))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("Could not find product with productId: " +  unknownProductId);
    }
}
