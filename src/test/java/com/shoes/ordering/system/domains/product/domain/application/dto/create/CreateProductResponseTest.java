package com.shoes.ordering.system.domains.product.domain.application.dto.create;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CreateProductResponseTest {
    @Test
    @DisplayName("정상 CreateProductResponse 생성")
    public void createProductResponseTest1() {

        // given
        String testProductName = "Test ProductName";
        ProductCategory testProductCategory = ProductCategory.SHOES;
        String testProductDescription = "Test Product Description";
        Money testProductPrice = new Money(new BigDecimal("200.00"));

        // when
        CreateProductResponse createProductResponse = CreateProductResponse.builder()
                .name(testProductName)
                .productCategory(testProductCategory)
                .description(testProductDescription)
                .price(testProductPrice)
                .build();

        // then
        assertThat(createProductResponse).isNotNull();
        assertThat(createProductResponse.getName()).isEqualTo(testProductName);
    }

    @Test
    @DisplayName("비정상 CreateProductCommand 생성: 프로퍼티에 값에는 Null 이 올 수 없다.")
    public void createProductResponseTest2() {

        //given
        ProductCategory testProductCategory = ProductCategory.SHOES;
        String testProductDescription = "Test Product Description";
        Money testProductPrice = new Money(new BigDecimal("200.00"));

        //when, then
        assertThatThrownBy(() -> CreateProductResponse.builder()
                .name(null) // name 프로퍼티 누락
                .productCategory(testProductCategory)
                .description(testProductDescription)
                .price(testProductPrice)
                .build()
        ).isInstanceOf(ConstraintViolationException.class);
    }
}
