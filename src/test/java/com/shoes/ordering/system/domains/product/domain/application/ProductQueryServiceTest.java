package com.shoes.ordering.system.domains.product.domain.application;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.ports.input.service.ProductQueryService;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.entity.ProductImage;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class ProductQueryServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductQueryService productQueryService;

    private TrackProductQuery trackProductQuery;
    private TrackProductListQuery trackProductListQuery;
    private Product product1;
    private Product product2;
    private List<Product> productList;
    private final List<ProductCategory> validProductCategoryList
            = List.of(ProductCategory.SHOES, ProductCategory.CLOTHING);

    @BeforeEach
    public void init() {
        product1 = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .name("Test name")
                .productCategory(ProductCategory.SHOES)
                .description("Test Product Description")
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

        when(productRepository.save(any(Product.class))).thenReturn(product1);
        when(productRepository.findByProductId(product1.getId().getValue())).thenReturn(Optional.of(product1));
        when(productRepository.findByProductCategory(validProductCategoryList)).thenReturn(Optional.of(productList));
    }

    @Test
    @DisplayName("정상 TrackProductResponse 생성 확인")
    public void trackProductTest() {
        // given
        trackProductQuery = TrackProductQuery.builder()
                .productId(product1.getId().getValue())
                .build();

        // when
         TrackProductResponse ResultTrackProductResponse = productQueryService.trackProduct(trackProductQuery);

        // then
        assertThat(ResultTrackProductResponse).isNotNull();
        assertThat(ResultTrackProductResponse.getProductId()).isEqualTo(product1.getId().getValue());
    }

    @Test
    @DisplayName("정상 TrackProductListResponse 생성 확인")
    public void trackProductListResponseTest() {
        // given
        trackProductListQuery = TrackProductListQuery.builder()
                .productCategoryList(validProductCategoryList)
                .build();

        // when
        TrackProductListResponse resultTrackProductListResponse
                = productQueryService.trackProductWithCategory(trackProductListQuery);

        // then
        assertThat(resultTrackProductListResponse).isNotNull();
        assertThat(resultTrackProductListResponse.getProductList().size()).isEqualTo(productList.size());
    }
}
