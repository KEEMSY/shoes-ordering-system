package com.shoes.ordering.system.domains.product.domain.application.dto.track;

import com.shoes.ordering.system.domains.product.domain.application.dto.ProductDTOException;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class TrackProductListQueryTest {

    @Test
    @DisplayName("정상 TrackProductListQuery 생성 확인")
    public void trackProductListQueryTest() {
        // given
        List<ProductCategory> validProductCategoryList = List.of(ProductCategory.SHOES, ProductCategory.CLOTHING);

        // when
        TrackProductListQuery trackProductListQuery = TrackProductListQuery.builder()
                .productCategoryList(validProductCategoryList)
                .build();

        // then
        assertThat(trackProductListQuery).isNotNull();
    }

    @Test
    @DisplayName("비정상 TrackProductListQuery 생성 확인: ProductCategory 는 null 일 수 없다.")
    public void invalidTrackProductListQueryTest1() {
        // given
        List<ProductCategory> invalidProductCategoryList = null;

        // when, then
         assertThatThrownBy(() ->TrackProductListQuery.builder()
                 .productCategoryList(invalidProductCategoryList)
                 .build()).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("비정상 TrackProductListQuery 에러 생성 확인: 이용 불가한 ProductCategory(단일) 로는 조회 할 수 없다.1")
    public void invalidTrackProductListQueryTest2() {
        // given
        List<ProductCategory> invalidProductCategoryList = List.of(ProductCategory.DISABLING);

        // when, then
        assertThatThrownBy(() ->TrackProductListQuery.builder()
                .productCategoryList(invalidProductCategoryList)
                .build()).isInstanceOf(ProductDTOException.class);
    }

    @Test
    @DisplayName("비정상 TrackProductListQuery 에러 생성 확인: 이용 불가한 ProductCategory(다수) 로는 조회 할 수 없다.2")
    public void invalidTrackProductListQueryTest3() {
        // given
        List<ProductCategory> invalidProductCategoryList = List.of(ProductCategory.SHOES, ProductCategory.DISABLING);

        // when, then
        assertThatThrownBy(() ->TrackProductListQuery.builder()
                .productCategoryList(invalidProductCategoryList)
                .build()).isInstanceOf(ProductDTOException.class);
    }
}
