package com.shoes.ordering.system.domains.product.domain.application.dto.track;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.entity.ProductImage;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductImageId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TrackProductListResponseTest {

    private Product product1;
    private Product product2;

    @BeforeEach
    public void init() {
        product1 = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .productCategory(ProductCategory.SHOES)
                .name("TestProductName1")
                .description("Test Product1 Description")
                .price(new Money(new BigDecimal("200.00")))
                .build();

        product2 = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .productCategory(ProductCategory.SHOES)
                .name("TestProductName2")
                .description("Test Product2 Description")
                .price(new Money(new BigDecimal("200.00")))
                .build();
    }

    @Test
    @DisplayName("정상 TrackProductListResponse 생성 확인")
    public void trackProductListResponseTest() {
        // given
        List<Product> products = List.of(product1, product2);

        // when
        TrackProductListResponse trackProductListResponse = TrackProductListResponse.builder()
                .productList(products)
                .build();
        List<Product> resultProductList = trackProductListResponse.getProductList();

        // then
        assertThat(resultProductList.get(0).getId().getValue()).isEqualTo(products.get(0).getId().getValue());
        assertThat(resultProductList.get(1).getId().getValue()).isEqualTo(products.get(1).getId().getValue());
    }


    @Test
    @DisplayName("비정상 TrackProductListResponse 에러 확인: 프로퍼티에는 null 이 올 수 없다.")
    public void invalidTrackProductListResponseTest() {
        // given
        List<Product> products = null;

        // when, then
        assertThatThrownBy(() -> TrackProductListResponse.builder()
                .productList(products)
                .build()).isInstanceOf(ConstraintViolationException.class);
    }
}
