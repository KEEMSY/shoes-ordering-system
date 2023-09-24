package com.shoes.ordering.system.domains.product.domain.application.dto.track;


import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.ProductDTOException;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DynamicSearchProductQueryTest {

    private final int NAME_LENGTH_LIMIT = 255;
    private final String NAME = "TestProductName";
    private final List<String> INPUT_VALID_PRODUCTCATEGORYLIST= List.of(ProductCategory.SHOES.toString(), ProductCategory.CLOTHING.toString());
    private final Money MIN_PRICE = new Money(new BigDecimal("0.01"));
    private final Money MAX_PRICE = new Money(new BigDecimal("200.00"));
    @Test
    @DisplayName("정상 DynamicSearchProductQuery 생성 확인: 입력 값이 존재할 경우")
    void dynamicSearchProductQueryTest() {
        // given

        // when
        DynamicSearchProductQuery dynamicSearchProductQuery = DynamicSearchProductQuery.builder()
                .name(NAME)
                .minPrice(MIN_PRICE)
                .maxPrice(MAX_PRICE)
                .productCategoryList(INPUT_VALID_PRODUCTCATEGORYLIST)
                .build();

        // then
        assertThat(dynamicSearchProductQuery).isNotNull();
    }

    @Test
    @DisplayName("정상 DynamicSearchProductQuery 생성 확인: 입력 값이 존재하지 않을 경우")
    void dynamicSearchProductQueryTest_NoInput() {
        // when
        DynamicSearchProductQuery dynamicSearchProductQuery = DynamicSearchProductQuery.builder()
                .name(NAME)
                .build();

        // then
        assertThat(dynamicSearchProductQuery).isNotNull();
    }

    @Test
    @DisplayName("정상 DynamicSearchProductQuery 에러 확인: name 의 길이는 255를 넘을 수 없다.")
    void dynamicSearchProductQueryErrorTest_WrongName() {
        // given
        String wrongName = new String(new char[NAME_LENGTH_LIMIT + 1]).replace('\0', 'A');

        // when, then
        assertThatThrownBy(() -> DynamicSearchProductQuery.builder()
                .name(wrongName)
                .build()).isInstanceOf(ProductDTOException.class);
    }

    @Test
    @DisplayName("정상 DynamicSearchProductQuery 에러 확인: 유효한 ProductCategory 인 경우에만 조회 가능하다.")
    void dynamicSearchProductQueryErrorTest_WrongProductCategory() {
        // given
        List<String> inputInvalidProductCategoryList = List.of("WRONG_PRODUCT_CATEGORY1", ProductCategory.SHOES.toString());

        // when, then
        assertThatThrownBy(() -> DynamicSearchProductQuery.builder()
                .productCategoryList(inputInvalidProductCategoryList)
                .build()).isInstanceOf(ProductDTOException.class);
    }

    @Test
    @DisplayName("정상 DynamicSearchProductQuery 에러 확인: Price 는 0 보다 작을 수 없다.")
    void dynamicSearchProductQueryErrorTest_WrongPrice() {
        // given
        Money wrongMinPrice = new Money(new BigDecimal("-200.00"));

        // when, then
        assertThatThrownBy(() -> DynamicSearchProductQuery.builder()
                .minPrice(wrongMinPrice)
                .build()).isInstanceOf(ProductDTOException.class);
    }

    @Test
    @DisplayName("정상 DynamicSearchProductQuery 에러 확인: minPrice 는 maxPrice 보다 클 수 없다.")
    void dynamicSearchProductQueryErrorTest_WrongMinPrice() {
        // given
        Money wrongMinPrice = MAX_PRICE.multiply(2);

        // when, then
        assertThatThrownBy(() -> DynamicSearchProductQuery.builder()
                .minPrice(wrongMinPrice)
                .maxPrice(MAX_PRICE)
                .build()).isInstanceOf(ProductDTOException.class);

    }

    @Test
    @DisplayName("정상 DynamicSearchProductQuery 에러 확인: maxPrice 는 minPrice 보다 작을 수 없다.")
    void dynamicSearchProductQueryErrorTest_WrongMaxPrice() {
        // given
        Money wrongMinPrice = MIN_PRICE.multiply(2);
        Money wrongMaxPrice = MIN_PRICE;

        // when, then
        assertThatThrownBy(() -> DynamicSearchProductQuery.builder()
                .minPrice(wrongMinPrice)
                .maxPrice(wrongMaxPrice)
                .build()).isInstanceOf(ProductDTOException.class);
    }
}