package com.shoes.ordering.system.domains.product.adapter.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.ports.input.service.ProductApplicationService;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = TestConfiguration.class)
@AutoConfigureMockMvc
public class ProductCommandControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductApplicationService productApplicationService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("정상 Product 생성 확인")
    public void createProductTest() throws Exception {
        // given
        CreateProductCommand createProductCommand = CreateProductCommand.builder()
                .name("Product 1")
                .description("Description 1")
                .price(new BigDecimal("100.00"))
                .productCategory(ProductCategory.SHOES)
                .build();

        CreateProductResponse expectedResponse = CreateProductResponse.builder()
                .productId(UUID.randomUUID())
                .name("Product 1")
                .description("Description 1")
                .price(new Money(new BigDecimal("100.00")))
                .productCategory(ProductCategory.SHOES)
                .build();

        when(productApplicationService.createProduct(any(CreateProductCommand.class))).thenReturn(expectedResponse);

        // Convert the createProductCommand to JSON
        String content = asJsonString(createProductCommand);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/products")
                        .header("Content-Type", "application/json")
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.productId").value(expectedResponse.getProductId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product 1"));

        ArgumentCaptor<CreateProductCommand> createProductCommandCaptor = ArgumentCaptor.forClass(CreateProductCommand.class);
        verify(productApplicationService).createProduct(createProductCommandCaptor.capture());

        CreateProductCommand capturedCreateProductCommand = createProductCommandCaptor.getValue();
        assertThat(capturedCreateProductCommand.getName()).isEqualTo(createProductCommand.getName());
    }

    @Test
    @DisplayName("정상 Product 업데이트 확인")
    public void updateProductTest() throws Exception {
        // given
        UUID productId = UUID.randomUUID();
        UpdateProductCommand updateProductCommand = UpdateProductCommand.builder()
                .productId(productId)
                .name("Updated Product")
                .description("Updated Description")
                .price(new BigDecimal("150.00"))
                .productCategory(ProductCategory.SHOES)
                .build();

        UpdateProductResponse expectedResponse = UpdateProductResponse.builder()
                .productId(productId)
                .name("Updated Product")
                .description("Updated Description")
                .price(new Money(new BigDecimal("150.00")))
                .productCategory(ProductCategory.SHOES)
                .build();

        when(productApplicationService.updateProduct(any(UpdateProductCommand.class))).thenReturn(expectedResponse);

        // Convert the updateProductCommand to JSON
        String content = asJsonString(updateProductCommand);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/products/{productId}", productId)
                        .header("Content-Type", "application/json")
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price.amount").value(150.00));

        ArgumentCaptor<UpdateProductCommand> updatedProductCommandCaptor = ArgumentCaptor.forClass(UpdateProductCommand.class);
        verify(productApplicationService).updateProduct(updatedProductCommandCaptor.capture());

        UpdateProductCommand capturedUpdateProductCommand = updatedProductCommandCaptor.getValue();
        assertThat(productId).isEqualTo(capturedUpdateProductCommand.getProductId());

    }

    // 객체를 JSON String 으로 변환
    private String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
