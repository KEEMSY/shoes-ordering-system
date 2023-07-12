package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.adapter;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.ProductEntity;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.mapper.ProductDataAccessMapper;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductJpaRepository;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductQuerydslRepository;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestConfiguration.class)
class ProductPersistenceAdapterTest {

    @Autowired
    ProductQuerydslRepository productQuerydslRepository;
    @Autowired
    ProductJpaRepository productJpaRepository;
    @Autowired
    ProductDataAccessMapper productDataAccessMapper;

    @AfterEach
    public void clean() {
        productJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("정상 save 테스트")
    void saveTest() {
        // given
        ProductEntity productEntity = ProductEntity.builder()
                .productId(UUID.randomUUID())
                .productCategory(ProductCategory.SHOES)
                .name("TestName")
                .description("Test Product Description")
                .price(new BigDecimal("200.00"))
//                .productImages(List.of(new ProductImage(new ProductImageId(UUID.randomUUID()), "TestUrl")))
                .build();

        // when
        productJpaRepository.save(productEntity);

        // then
        List<ProductEntity> results = productJpaRepository.findAll();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getProductId()).isNotNull();
        assertThat(results.get(0).getName()).isEqualTo("TestName");
    }

    @Test
    void findByProductIdTest() {
        // given

        // when

        // then
    }

    @Test
    void findByProductCategoryTest() {
    }
}