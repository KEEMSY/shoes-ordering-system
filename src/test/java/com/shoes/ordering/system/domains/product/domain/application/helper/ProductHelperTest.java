package com.shoes.ordering.system.domains.product.domain.application.helper;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.mapper.ProductDataMapper;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductUpdatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class ProductHelperTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDataMapper productDataMapper;
    @Autowired
    private ProductHelper productHelper;

    private Product product;
    private CreateProductCommand createProductCommand;
    private UpdateProductCommand updateProductCommand;

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
        product.initializeProduct();
        product.validateProduct();

        updateProductCommand = UpdateProductCommand.builder()
                 .productId(product.getId().getValue())
                 .name("UpdateTestName")
                 .productCategory(ProductCategory.SHOES)
                 .description("Update Test Description")
                 .price(new Money(new BigDecimal("100.00")))
                 .productImages(List.of("testURL1", "testURL2"))
                 .build();

         when(productRepository.save(any(Product.class))).thenReturn(product);
         when(productRepository.findByProductId(product.getId().getValue())).thenReturn(Optional.ofNullable(product));
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

    @Test
    @DisplayName("정상 UpdateProductEvent 생성 확인")
    public void updateProductPersistTest() {
        // given: BeforeEach 에 포함

        // when
        ProductUpdatedEvent resultProductUpdatedEvent = productHelper.updateProductPersist(updateProductCommand);
        Product updatedProduct = resultProductUpdatedEvent.getProduct();

        // then
        assertThat(resultProductUpdatedEvent.getProduct()).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo(updateProductCommand.getName());
        assertThat(updatedProduct.getDescription()).isEqualTo(updateProductCommand.getDescription());

    }
}
