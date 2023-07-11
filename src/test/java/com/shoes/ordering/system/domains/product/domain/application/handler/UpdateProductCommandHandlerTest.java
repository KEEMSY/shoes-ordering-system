package com.shoes.ordering.system.domains.product.domain.application.handler;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.mapper.ProductDataMapper;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.entity.ProductImage;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductImageId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class UpdateProductCommandHandlerTest {

    @Autowired
    private UpdateProductCommandHandler updateProductCommandHandler;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    ProductDataMapper productDataMapper;

    private Product product;

    @BeforeEach
    public void init() {
        product = Product.builder()
                .productId(new ProductId(UUID.randomUUID()))
                .name("Test name")
                .productCategory(ProductCategory.SHOES)
                .description("Test Description")
                .price(new Money(new BigDecimal("200.00")))
//                .productImages(List.of(new ProductImage(new ProductImageId(UUID.randomUUID()), "TestUrl")))
                .build();

        product.initializeProduct();
        product.validateProduct();

        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productRepository.findByProductId(product.getId().getValue())).thenReturn(Optional.of(product));
    }

    @Test
    @DisplayName("정상 UpdateProductResponse 생성 확인")
    public void updateProductTest() {
        // given
        UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                .productId(product.getId().getValue())
                .name("UpdateTestName")
                .productCategory(ProductCategory.SHOES)
                .description("Update Test Description")
                .price(new Money(new BigDecimal("100.00")))
//                .productImages(List.of("TestUrl"))
                .build();

        // when
        UpdateProductResponse resultUpdateProductResponse
                = updateProductCommandHandler.updateProduct(updateProductCommand);

        // then
        assertThat(resultUpdateProductResponse.getName()).isNotEqualTo(product.getName());

        assertThat(resultUpdateProductResponse).isNotNull();
        assertThat(resultUpdateProductResponse.getName()).isEqualTo(updateProductCommand.getName());
        assertThat(resultUpdateProductResponse.getDescription()).isEqualTo(updateProductCommand.getDescription());
    }

}
