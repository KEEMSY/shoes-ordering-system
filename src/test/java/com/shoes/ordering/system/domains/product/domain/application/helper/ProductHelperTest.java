package com.shoes.ordering.system.domains.product.domain.application.helper;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.mapper.ProductDataMapper;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.ProductDomainService;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class ProductHelperTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDomainService productDomainService;
    @Autowired
    private ProductDataMapper productDataMapper;
    @Autowired
    private ProductHelper productHelper;

    private Product product;
    private CreateProductCommand createProductCommand;

    @BeforeEach
    public void init() {
         createProductCommand = CreateProductCommand.builder()
                .name("TestName")
                .productCategory(ProductCategory.SHOES)
                .description("Test Description")
                .price(new Money(new BigDecimal("200.00")))
                .productImages(List.of("testURL1", "testURL2"))
                .build();

         product = productDataMapper.creatProductCommandToProduct(createProductCommand);

         when(productRepository.save(any(Product.class))).thenReturn(product);
    }

    @Test
    @DisplayName("정상 CreateProductEvent 생성 확인")
    public void persistProductTest() {
        // given: BeforeEach 내 포함

        // when
        ProductCreatedEvent resultProductCreatedEvent = productHelper.persistProduct(createProductCommand);

        // then
        Product resultProduct = resultProductCreatedEvent.getProduct();
        assertThat(resultProduct.getName()).isEqualTo(createProductCommand.getName());
        assertThat(resultProduct.getDescription()).isEqualTo(createProductCommand.getDescription());
    }
}
