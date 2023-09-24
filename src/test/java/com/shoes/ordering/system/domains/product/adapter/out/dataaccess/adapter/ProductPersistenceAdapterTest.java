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

    private final BigDecimal PRODUCT_PRICE = new BigDecimal("200.00");

    @AfterEach
    public void clean() {
        productJpaRepository.deleteAll();
    }
    private ProductEntity createProductEntity(UUID productId, String name, ProductCategory productCategory, BigDecimal price) {
        return ProductEntity.builder()
                .productId(productId)
                .productCategory(productCategory)
                .name(name)
                .description("Test Product Description")
                .price(price)
                .build();
    }

    private Product createProduct(ProductId productId, String name, ProductCategory productCategory) {
        return Product.builder()
                .productId(productId)
                .productCategory(productCategory)
                .name(name)
                .description("Test Product Description")
                .price(new Money(PRODUCT_PRICE))
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

        ProductEntity productEntity1 = createProductEntity(productId, "TestName1", ProductCategory.SHOES, PRODUCT_PRICE);
        ProductEntity productEntity2 = createProductEntity(UUID.randomUUID(), "TestName2", ProductCategory.CLOTHING, PRODUCT_PRICE);

        productJpaRepository.saveAll(List.of(productEntity1, productEntity2));

        // when
        Optional<Product> result = productPersistenceAdapter.findByProductId(productEntity1.getProductId());

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("TestName1");
    }

    @Test
    @DisplayName("정상 Product 카테고리 조회")
    void findByProductCategoryTest() {
        // given
        ProductEntity shoesProductEntity1
                = createProductEntity(UUID.randomUUID(), "TestName1", ProductCategory.SHOES, PRODUCT_PRICE);
        ProductEntity shoesProductEntity2
                = createProductEntity(UUID.randomUUID(), "TestName2", ProductCategory.SHOES, PRODUCT_PRICE);

        ProductEntity clothingProductEntity1
                = createProductEntity(UUID.randomUUID(), "TestName3", ProductCategory.CLOTHING, PRODUCT_PRICE);

        productJpaRepository.saveAll(List.of(shoesProductEntity1, shoesProductEntity2, clothingProductEntity1));

        List<ProductCategory> productCategoryWithShoes = List.of(ProductCategory.SHOES);
        List<ProductCategory> productCategoryList = List.of(ProductCategory.SHOES, ProductCategory.CLOTHING);

        // when
        Optional<List<Product>> resultsWithShoesCategory
                = productPersistenceAdapter.findByProductCategory(productCategoryWithShoes);

        Optional<List<Product>> resultsWithAllCategories
                = productPersistenceAdapter.findByProductCategory(productCategoryList);

        // then
        resultsWithShoesCategory.ifPresent(products -> assertThat(products.size()).isEqualTo(2));
        resultsWithAllCategories.ifPresent(products -> assertThat(products.size()).isEqualTo(3));
    }

    @Test
    @DisplayName("정상 searchProductsByDynamicQuery 테스트: 가격 범주 내 Product 조회 확인")
    void searchProductsByDynamicQueryTest_Price() {
        // given
        ProductEntity shoesProductEntity1
                = createProductEntity(UUID.randomUUID(), "TestName1", ProductCategory.SHOES, PRODUCT_PRICE);
        ProductEntity shoesProductEntity2
                = createProductEntity(UUID.randomUUID(), "TestName2", ProductCategory.SHOES, PRODUCT_PRICE.add(new BigDecimal("100.00")));

        ProductEntity clothingProductEntity1
                = createProductEntity(UUID.randomUUID(), "TestName3", ProductCategory.CLOTHING, PRODUCT_PRICE);

        productJpaRepository.saveAll(List.of(shoesProductEntity1, shoesProductEntity2, clothingProductEntity1));


        List<ProductCategory> productCategoryList = List.of(ProductCategory.SHOES, ProductCategory.CLOTHING);
        ProductSearchPersistenceRequest productSearchPersistenceRequest = ProductSearchPersistenceRequest.builder()
                .minPrice(new Money(new BigDecimal("0.00")))
                .maxPrice(new Money(PRODUCT_PRICE))
                .productCategoryList(productCategoryList)
                .build();

        // when
        List<Product> result
                = productPersistenceAdapter.searchProductsByDynamicQuery(productSearchPersistenceRequest);

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("정상 searchProductsByDynamicQuery 테스트: 정상 ProductCategory 내 Product 조회 확인")
    void searchProductsByDynamicQueryTest_ProductCategoryList() {
        // given
        ProductEntity shoesProductEntity1
                = createProductEntity(UUID.randomUUID(), "TestName1", ProductCategory.SHOES, PRODUCT_PRICE);
        ProductEntity shoesProductEntity2
                = createProductEntity(UUID.randomUUID(), "TestName2", ProductCategory.SHOES, PRODUCT_PRICE.add(new BigDecimal("100.00")));

        ProductEntity clothingProductEntity1
                = createProductEntity(UUID.randomUUID(), "TestName3", ProductCategory.CLOTHING, PRODUCT_PRICE);

        productJpaRepository.saveAll(List.of(shoesProductEntity1, shoesProductEntity2, clothingProductEntity1));


        List<ProductCategory> productCategoryWithShoes = List.of(ProductCategory.SHOES);
        ProductSearchPersistenceRequest productSearchPersistenceRequest = ProductSearchPersistenceRequest.builder()
                .minPrice(new Money(new BigDecimal("0.00")))
                .maxPrice(new Money(PRODUCT_PRICE))
                .productCategoryList(productCategoryWithShoes)
                .build();

        // when
        List<Product> result
                = productPersistenceAdapter.searchProductsByDynamicQuery(productSearchPersistenceRequest);

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("정상 searchProductsByDynamicQuery 테스트: Name 조회는 대소문자를 구분하지 않는다.")
    void searchProductsByDynamicQueryTest_Name() {
        // given
        ProductEntity shoesProductEntity1
                = createProductEntity(UUID.randomUUID(), "TestName1", ProductCategory.SHOES, PRODUCT_PRICE);
        ProductEntity shoesProductEntity2
                = createProductEntity(UUID.randomUUID(), "testName1", ProductCategory.SHOES, PRODUCT_PRICE);

        ProductEntity clothingProductEntity1
                = createProductEntity(UUID.randomUUID(), "TestName1", ProductCategory.CLOTHING, PRODUCT_PRICE);

        productJpaRepository.saveAll(List.of(shoesProductEntity1, shoesProductEntity2, clothingProductEntity1));


        List<ProductCategory> productCategoryWithShoes = List.of(ProductCategory.SHOES);
        ProductSearchPersistenceRequest productSearchPersistenceRequest = ProductSearchPersistenceRequest.builder()
                .minPrice(new Money(new BigDecimal("0.00")))
                .maxPrice(new Money(PRODUCT_PRICE))
                .productCategoryList(productCategoryWithShoes)
                .build();

        // when
        List<Product> result
                = productPersistenceAdapter.searchProductsByDynamicQuery(productSearchPersistenceRequest);

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("정상 searchProductsByDynamicQuery 테스트: Product 가 존재하지 않을 경우 빈 리스트를 반환한다.")
    void searchProductsByDynamicQueryTest_Empty() {
        // given
        ProductEntity shoesProductEntity1
                = createProductEntity(UUID.randomUUID(), "TestName1", ProductCategory.SHOES, PRODUCT_PRICE);
        ProductEntity shoesProductEntity2
                = createProductEntity(UUID.randomUUID(), "testName1", ProductCategory.SHOES, PRODUCT_PRICE);

        ProductEntity clothingProductEntity1
                = createProductEntity(UUID.randomUUID(), "TestName1", ProductCategory.CLOTHING, PRODUCT_PRICE);

        productJpaRepository.saveAll(List.of(shoesProductEntity1, shoesProductEntity2, clothingProductEntity1));


        List<ProductCategory> productCategoryWithShoes = List.of(ProductCategory.SHOES);
        ProductSearchPersistenceRequest productSearchPersistenceRequest = ProductSearchPersistenceRequest.builder()
                .minPrice(new Money(PRODUCT_PRICE.add(new BigDecimal("100.00"))))
                .maxPrice(new Money(PRODUCT_PRICE.add(new BigDecimal("200.00"))))
                .productCategoryList(productCategoryWithShoes)
                .build();

        // when
        List<Product> result
                = productPersistenceAdapter.searchProductsByDynamicQuery(productSearchPersistenceRequest);

        // then
        assertThat(result).isEmpty();
    }
}