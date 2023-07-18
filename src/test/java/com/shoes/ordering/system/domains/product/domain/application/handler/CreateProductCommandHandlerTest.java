package com.shoes.ordering.system.domains.product.domain.application.handler;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.mapper.ProductDataMapper;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class CreateProductCommandHandlerTest {

    @Autowired
    private CreateProductCommandHandler createProductCommandHandler;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDataMapper productDataMapper;
    private CreateProductCommand createProductCommand;

    @BeforeEach
    public void init() {
        createProductCommand = CreateProductCommand.builder()
                .name("TestName")
                .productCategory(ProductCategory.SHOES)
                .description("Test Description")
                .price(new Money(new BigDecimal("200.00")))
                .build();

        Product product = productDataMapper.creatProductCommandToProduct(createProductCommand);

        when(productRepository.save(any(Product.class))).thenReturn(product);
    }

    @Test
    @DisplayName("정상 CreateProductResponse 생성 확인 ")
    public void createProductTest() {
        // given: BeforeEach 에 포함

        // when
        CreateProductResponse resultCreateProductResponse = createProductCommandHandler.createProduct(createProductCommand);

        // then
        assertThat(resultCreateProductResponse.getName()).isEqualTo(createProductCommand.getName());
        assertThat(resultCreateProductResponse.getDescription()).isEqualTo(createProductCommand.getDescription());
        assertThat(resultCreateProductResponse.getProductCategory()).isEqualTo(createProductCommand.getProductCategory());


    }
}
