package com.shoes.ordering.system.domains.product.domain.core;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.entity.ProductImage;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductUpdatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductImageId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductDomainServiceImplTest {

    private final ProductDomainService productDomainService = new ProductDomainServiceImpl();
    @Test
    @DisplayName("정상 ProductCreatedEvent 생성 확인")
    public void initiateProductTest() {

        // given
        Product product = Product.builder()
                .productCategory(ProductCategory.SHOES)
                .description("Test Product Description")
                .price(new Money(new BigDecimal("200.00")))
                .build();

        // when
        ProductCreatedEvent productCreatedEvent = productDomainService.validateAndInitiateProduct(product);

        // then
        assertThat(productCreatedEvent.getProduct()).isEqualTo(product);
        assertThat(productCreatedEvent.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("정상 ProductUpdatedEvent 생성 확인")
    public void validateAndUpdateProductTest() {
        // given
        ProductId existingProductId = new ProductId(UUID.randomUUID());
        Product updatedProduct = Product.builder()
                .productId(existingProductId)
                .productCategory(ProductCategory.SHOES)
                .description("Test Product Description")
                .price(new Money(new BigDecimal("200.00")))
                .build();

        // when
        ProductUpdatedEvent productUpdatedEvent = productDomainService.validateAndUpdateProduct(updatedProduct);

        // then
        assertThat(productUpdatedEvent.getProduct()).isEqualTo(updatedProduct);
        assertThat(productUpdatedEvent.getCreatedAt()).isNotNull();
    }
}
