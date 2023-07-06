package com.shoes.ordering.system.domains.product.domain.application.handler;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListResponse;
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
public class TrackProductListQueryHandlerTest {

    @Autowired
    private  TrackProductListQueryHandler trackProductListQueryHandler;
    @Autowired
    private  ProductRepository productRepository;

    private TrackProductListQuery trackProductListQuery;
    private List<Product> productList;
    private Product product1;
    private Product product2;

    private final List<ProductCategory> validProductCategoryList
            = List.of(ProductCategory.SHOES, ProductCategory.CLOTHING);
    private final List<ProductCategory> onlyOneProductCategoryList
            = List.of(ProductCategory.CLOTHING);

    @BeforeEach
    public void init() {
        product1 = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .productCategory(ProductCategory.SHOES)
                .name("TestProductName1")
                .description("Test Product1 Description")
                .price(new Money(new BigDecimal("200.00")))
                .productImages(List.of(new ProductImage(new ProductImageId(UUID.randomUUID()), "TestUrl")))
                .build();

        product2 = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .productCategory(ProductCategory.SHOES)
                .name("TestProductName2")
                .description("Test Product2 Description")
                .price(new Money(new BigDecimal("200.00")))
                .productImages(List.of(new ProductImage(new ProductImageId(UUID.randomUUID()), "TestUrl")))
                .build();

        productList = List.of(product1, product2);

        when(productRepository.findByProductCategory(validProductCategoryList)).thenReturn(Optional.of(productList));
    }

    @Test
    @DisplayName("정상 trackProductWithCategory 확인")
    public void trackProductWithCategoryTest() {
        // given
        trackProductListQuery = TrackProductListQuery.builder()
                .productCategoryList(validProductCategoryList)
                .build();

        // when
        TrackProductListResponse resultTrackProductListResponse
                = trackProductListQueryHandler.trackProductWithCategory(trackProductListQuery);
        List<Product> resultProductList = resultTrackProductListResponse.getProductList();

        // then
        assertThat(resultTrackProductListResponse).isNotNull();
        assertThat(resultProductList.size()).isEqualTo(productList.size());
    }

    @Test
    @DisplayName("비정상 trackProductWithCategory 예외 확인: Product 가 존재하지 않을 경우")
    public void invalidTrackProductWithCategoryTest() {
        // given
        trackProductListQuery = TrackProductListQuery.builder()
                .productCategoryList(onlyOneProductCategoryList)
                .build();

        // when, then
        assertThatThrownBy(()-> trackProductListQueryHandler.trackProductWithCategory(trackProductListQuery))
                .isInstanceOf(ProductNotFoundException.class);
    }
}