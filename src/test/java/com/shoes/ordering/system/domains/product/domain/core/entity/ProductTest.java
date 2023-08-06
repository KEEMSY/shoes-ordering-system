package com.shoes.ordering.system.domains.product.domain.core.entity;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.exception.ProductDomainException;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductImageId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ProductTest {

    @Test
    @DisplayName("정상 Product 생성 확인")
    public void initializeProductTest() {
        // given
        Product product = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .productCategory(ProductCategory.SHOES)
                .description("Test Product Description")
                .price(new Money(new BigDecimal("200.00")))
                .build();
        // when
        product.initializeProduct();
        product.validateProduct();

        // then
        assertThat(product.getId()).isNotNull();
    }

    @Test
    @DisplayName("비정상 Product 생성확인: Invalid price")
    public void validatePriceTest() {
        // given
        Money invalidMoney = new Money(new BigDecimal("-200.00"));

        // when
        Product invalidPriceProduct = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .productCategory(ProductCategory.SHOES)
                .description("Test Product Description")
                .price(invalidMoney)
                .build();
        invalidPriceProduct.initializeProduct();

        // then
        assertThatThrownBy(invalidPriceProduct::validateProduct).isInstanceOf(ProductDomainException.class)
                .hasMessageContaining("Price must be greater than zero!");

    }

    @Test
    @DisplayName("비정상 Product 생성확인: Invalid Category")
    public void validateProductCategoryTest() {
        // given
        ProductCategory invalidProductCategory = ProductCategory.DISABLING;

        // when
        Product invalidPriceProduct = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .productCategory(invalidProductCategory)
                .description("Test Product Description")
                .price(new Money(new BigDecimal("200.00")))
                .build();
        invalidPriceProduct.initializeProduct();

        // then
        assertThatThrownBy(invalidPriceProduct::validateProduct).isInstanceOf(ProductDomainException.class)
                .hasMessageContaining("Product is not a valid category for creating the product");

    }
}
