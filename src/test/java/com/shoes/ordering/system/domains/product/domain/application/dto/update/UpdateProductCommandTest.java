package com.shoes.ordering.system.domains.product.domain.application.dto.update;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UpdateProductCommandTest {

    @Test
    @DisplayName("정상 UpdateProductCommand 테스트")
    public void updateProductCommandTest1() {
        // given
        UUID productId = UUID.randomUUID();
        String testProductName = "Test ProductName";
        ProductCategory testProductCategory = ProductCategory.SHOES;
        String testProductDescription = "Test Product Description";
        Money testProductPrice = new Money(new BigDecimal("200.00"));

        // when
        UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                .productId(productId)
                .name(testProductName)
                .productCategory(testProductCategory)
                .description(testProductDescription)
                .price(testProductPrice)
                .build();

        // then
        assertThat(updateProductCommand).isNotNull();
        assertThat(updateProductCommand.getName()).isEqualTo(testProductName);
    }

    @Test
    @DisplayName("비정상 UpdateProductCommand 생성: 프로퍼티에 값에는 Null 이 올 수 없다.")
    public void updateProductCommandTest2() {

        //given
        String testProductName = "Test ProductName";
        ProductCategory testProductCategory = ProductCategory.SHOES;
        String testProductDescription = "Test Product Description";
        Money testProductPrice = new Money(new BigDecimal("200.00"));

        //when, then
        assertThatThrownBy(() -> UpdateProductCommand.builder()
                .productId(null) // productId 누락
                .name(testProductName)
                .productCategory(testProductCategory)
                .description(testProductDescription)
                .price(testProductPrice)
                .build()
        ).isInstanceOf(ConstraintViolationException.class);
    }
}