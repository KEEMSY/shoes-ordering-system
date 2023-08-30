package com.shoes.ordering.system.domains.product.domain.application.handler;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.mapper.ProductDataMapper;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.message.publisher.ProductCreatedRequestMessagePublisher;
import com.shoes.ordering.system.domains.product.domain.application.ports.output.repository.ProductRepository;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.event.ProductCreatedEvent;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class CreateProductCommandHandlerTest {

    @Autowired
    private CreateProductCommandHandler createProductCommandHandler;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDataMapper productDataMapper;
    @MockBean
    private ProductCreatedRequestMessagePublisher productCreatedRequestMessagePublisher;

    @Test
    @DisplayName("정상 CreateProductResponse 생성 확인 ")
    public void createProductTest() {
        // given
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .name("TestName")
                .productCategory(ProductCategory.SHOES)
                .description("Test Description")
                .price(new BigDecimal("200.00"))
                .build();

        Product product = productDataMapper.creatProductCommandToProduct(createProductCommand);
        when(productRepository.save(any(Product.class))).thenReturn(product);
        doNothing().when(productCreatedRequestMessagePublisher).publish(any(ProductCreatedEvent.class));

        // when
        CreateProductResponse resultCreateProductResponse = createProductCommandHandler.createProduct(createProductCommand);

        // then
        // 상호작용 확인
        ArgumentCaptor<ProductCreatedEvent> createdProductEventCaptor = ArgumentCaptor.forClass(ProductCreatedEvent.class);
        verify(productCreatedRequestMessagePublisher).publish(createdProductEventCaptor.capture());

        ProductCreatedEvent capturedProductCreatedEvent = createdProductEventCaptor.getValue();
        assertThat(capturedProductCreatedEvent.getProduct().getId().getValue()).isEqualTo(resultCreateProductResponse.getProductId());
        assertThat(capturedProductCreatedEvent.getProduct().getName()).isEqualTo(product.getName());

        assertThat(resultCreateProductResponse.getName()).isEqualTo(createProductCommand.getName());
        assertThat(resultCreateProductResponse.getDescription()).isEqualTo(createProductCommand.getDescription());
        assertThat(resultCreateProductResponse.getProductCategory()).isEqualTo(createProductCommand.getProductCategory());

    }
}
