package com.shoes.ordering.system.domains.product.adapter.in.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductResponse;
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

        // when and then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/products")
                        .header("Content-Type", "application/json")
                        .content(asJsonString(createProductCommand)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.productId").value(expectedResponse.getProductId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product 1"));

        ArgumentCaptor<CreateProductCommand> createProductCommandCaptor = ArgumentCaptor.forClass(CreateProductCommand.class);
        verify(productApplicationService).createProduct(createProductCommandCaptor.capture());

        CreateProductCommand capturedCreateProductCommand = createProductCommandCaptor.getValue();
        assertThat(capturedCreateProductCommand.getName()).isEqualTo(createProductCommand.getName());
    }

    // 객체를 JSON String 으로 변환
    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
