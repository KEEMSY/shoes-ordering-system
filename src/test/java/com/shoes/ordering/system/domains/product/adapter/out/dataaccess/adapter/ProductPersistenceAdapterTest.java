package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.adapter;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.ProductEntity;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.mapper.ProductDataAccessMapper;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductJpaRepository;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.respository.ProductQuerydslRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestConfiguration.class)
class ProductPersistenceAdapterTest {

    @Autowired
    ProductPersistenceAdapter productPersistenceAdapter;
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
    private ProductEntity createProductEntity(UUID productId, String name, ProductCategory productCategory) {
        return ProductEntity.builder()
                .productId(productId)
                .productCategory(productCategory)
                .name(name)
                .description("Test Product Description")
                .price(new BigDecimal("200.00"))
                .build();
    }

    private Product createProduct(ProductId productId, String name, ProductCategory productCategory) {
        return Product.builder()
                .productId(productId)
                .productCategory(productCategory)
                .name(name)
                .description("Test Product Description")
                .price(new Money(new BigDecimal("200.00")))
                .build();
    }
    @Test
    @DisplayName("정상 save 테스트")
    void saveTest() {
        // given
        Product product = createProduct(new ProductId(UUID.randomUUID()), "TestName", ProductCategory.SHOES);

        // when
        productPersistenceAdapter.save(product);

        // then
        List<ProductEntity> results = productJpaRepository.findAll();
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getProductId()).isNotNull();
        assertThat(results.get(0).getName()).isEqualTo("TestName");
    }

    @Test
    @DisplayName("정상 Product 조회 확인")
    void findByProductIdTest() {
        // given
        UUID productId = UUID.randomUUID();

        ProductEntity productEntity1 = createProductEntity(productId, "TestName1", ProductCategory.SHOES);
        ProductEntity productEntity2 = createProductEntity(UUID.randomUUID(), "TestName2", ProductCategory.CLOTHING);

        productJpaRepository.saveAll(List.of(productEntity1, productEntity2));

        // when
        Optional<Product> result = productPersistenceAdapter.findByProductId(productEntity1.getProductId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("TestName1");
    }

    @Test
    void findByProductCategoryTest() {
    }
}