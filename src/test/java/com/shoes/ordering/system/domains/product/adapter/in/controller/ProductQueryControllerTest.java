package com.shoes.ordering.system.domains.product.adapter.in.controller;

import com.shoes.ordering.system.TestConfiguration;
import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.*;
import com.shoes.ordering.system.domains.product.domain.application.ports.input.service.ProductQueryService;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.exception.ProductNotFoundException;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = TestConfiguration.class)
@AutoConfigureMockMvc
public class ProductQueryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductQueryService productQueryService;

    @Test
    @DisplayName("정상 Product 조회 확인")
    public void testGetProductByProductId() throws Exception {
        // given
        UUID targetProductId = UUID.randomUUID();
        TrackProductResponse expectedResponse = TrackProductResponse.builder()
                .productId(targetProductId)
                .name("Test Product")
                .productCategory(ProductCategory.SHOES)
                .description("Test Description")
                .price(new Money(new BigDecimal("200.00")))
                .build();

        // Stub: productQueryService.trackProduct()
        when(productQueryService.trackProduct(any(TrackProductQuery.class))).thenReturn(expectedResponse);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/{productId}", targetProductId)
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productCategory").value("SHOES"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Test Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price.amount").value(200.00));

        // ArgumentCaptor 를 사용하여 trackProduct 매서드에 전달된 실제 TrackProductQuery 객체 확인
        ArgumentCaptor<TrackProductQuery> trackProductQueryCaptor = ArgumentCaptor.forClass(TrackProductQuery.class);

        verify(productQueryService).trackProduct(trackProductQueryCaptor.capture());

        TrackProductQuery capturedTrackProductQuery = trackProductQueryCaptor.getValue();

        assertThat(targetProductId).isEqualTo(capturedTrackProductQuery.getProductId());

    }

    @Test
    @DisplayName("정상 Product 가 존재하지 않을 경우 에러 확인")
    public void testGetProductByProductId_ProductNotFoundException() throws Exception {
        // given
        UUID unknownProductId = UUID.randomUUID();
        String errorMessage = "Could not find product with productId: " + unknownProductId;

        // Stub: productQueryService.trackProduct()
        when(productQueryService.trackProduct(any(TrackProductQuery.class)))
                .thenThrow(new ProductNotFoundException(errorMessage));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/products/{productId}", unknownProductId)
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(errorMessage));
    }

    @Test
    @DisplayName("정상 ProductCategory 를 통한 Product 조회")
    public void testTrackProduct_Success() throws Exception {
        // given
        ProductId productId1 = new ProductId((UUID.randomUUID()));
        ProductId productId2 = new ProductId((UUID.randomUUID()));
        ProductCategory category1 = ProductCategory.SHOES;
        ProductCategory category2 = ProductCategory.CLOTHING;

        // Create sample products
        Product product1 = Product.builder()
                .productId(productId1)
                .name("Product 1")
                .productCategory(category1)
                .description("Description 1")
                .price(new Money(BigDecimal.valueOf(100)))
                .build();

        Product product2 = Product.builder()
                .productId(productId2)
                .name("Product 2")
                .productCategory(category2)
                .description("Description 2")
                .price(new Money(BigDecimal.valueOf(200)))
                .build();

        List<Product> productList = Arrays.asList(product1, product2);

        // Stub: productQueryService.trackProductWithCategory
        TrackProductListResponse expectedResponse = TrackProductListResponse.builder()
                .productList(productList)
                .build();
        when(productQueryService.trackProductWithCategory(any(TrackProductListQuery.class))).thenReturn(expectedResponse);

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search")
                        .param("trackProperties", "SHOES")
                        .param("trackProperties", "CLOTHING")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].id.value").value(productId1.getValue().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].id.value").value(productId2.getValue().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].id.value").value(productId1.getValue().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].name").value("Product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].productCategory").value(category1.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].description").value("Description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].price.amount").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].price.greaterThanZero").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].id.value").value(productId2.getValue().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].name").value("Product 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].productCategory").value(category2.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].description").value("Description 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].price.amount").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].price.greaterThanZero").value(true));
    }

    @Test
    @DisplayName("비정상 ProductCategory 를 통한 정상 에러 확인")
    public void testTrackProduct() throws Exception {
        // given
        String invalidCategory = "INVALID_CATEGORY";

        // When and Then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search")
                        .header("Content-Type", "application/json")
                        .param("trackProperties", "INVALID_CATEGORY"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Invalid product categories: " + invalidCategory));

    }
    @Test
    @DisplayName("정상 searchProductsByDynamicDefaultTest: 200 OK")
    void searchProductsByDynamicTest_Default() throws Exception {
        // given

        // Create sample products
        ProductCategory category1 = ProductCategory.SHOES;
        ProductCategory category2 = ProductCategory.CLOTHING;

        Product product1 = Product.builder()
                .productId(new ProductId((UUID.randomUUID())))
                .name("Product 1")
                .productCategory(category1)
                .description("Description 1")
                .price(new Money(BigDecimal.valueOf(100)))
                .build();

        Product product2 = Product.builder()
                .productId(new ProductId((UUID.randomUUID())))
                .name("Product 2")
                .productCategory(category2)
                .description("Description 2")
                .price(new Money(BigDecimal.valueOf(200)))
                .build();

        List<Product> productList = Arrays.asList(product1, product2);

        // Stub: productQueryService.searchProducts
        TrackProductListResponse expectedResponse = TrackProductListResponse.builder()
                .productList(productList)
                .build();
        when(productQueryService.searchProducts(any(DynamicSearchProductQuery.class))).thenReturn(expectedResponse);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search/dynamic")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].name").value("Product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].productCategory").value(category1.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].description").value("Description 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].price.amount").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].name").value("Product 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].productCategory").value(category2.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].description").value("Description 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].price.amount").value(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].price.greaterThanZero").value(true));
    }
    @Test
    @DisplayName("정상 searchProductsByDynamicTest: 200 OK")
    void searchProductsByDynamicTest() throws Exception {
        // given

        // Create sample products
        ProductCategory category1 = ProductCategory.SHOES;
        ProductCategory category2 = ProductCategory.CLOTHING;

        Product shoesProduct1 = Product.builder()
                .productId(new ProductId((UUID.randomUUID())))
                .name("shoesProduct1")
                .productCategory(category1)
                .description("shoesProduct1 Description")
                .price(new Money(BigDecimal.valueOf(100)))
                .build();

        Product shoesProduct2 = Product.builder()
                .productId(new ProductId((UUID.randomUUID())))
                .name("shoesProduct2")
                .productCategory(category1)
                .description("shoesProduct2 Description")
                .price(new Money(BigDecimal.valueOf(100)))
                .build();

        Product clothingProduct2 = Product.builder()
                .productId(new ProductId((UUID.randomUUID())))
                .name("clothingProduct2")
                .productCategory(category2)
                .description("clothingProduct2 Description")
                .price(new Money(BigDecimal.valueOf(200)))
                .build();

        List<Product> productList = Arrays.asList(shoesProduct1, shoesProduct2);

        // Stub: productQueryService.searchProducts
        TrackProductListResponse expectedResponse = TrackProductListResponse.builder()
                .productList(productList)
                .build();
        when(productQueryService.searchProducts(any(DynamicSearchProductQuery.class))).thenReturn(expectedResponse);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search/dynamic")
                        .param("name", "Product")
                        .param("productCategoryList", "SHOES")
                        .param("minPrice", "50.00")
                        .param("maxPrice", "200.00")
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].name").value("shoesProduct1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].productCategory").value(category1.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].description").value("shoesProduct1 Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[0].price.amount").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].name").value("shoesProduct2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].productCategory").value(category1.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].description").value("shoesProduct2 Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].price.amount").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productList[1].price.greaterThanZero").value(true));
    }

    @Test
    @DisplayName("정상 searchProductsByDynamicErrorTest: Input 값(minPrice)이 유효하지 않을 경우: 400 Bad Request")
    void searchProductsByDynamicErrorTest_InvalidMinPriceInput() throws Exception {
        // given
        BigDecimal invalidMinPrice = new BigDecimal("-100.00");

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search/dynamic")
                        .header("Content-Type", "application/json")
                        .param("minPrice", String.valueOf(invalidMinPrice)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("The minPrice should be greater then Zero!"));
    }

    @Test
    @DisplayName("정상 searchProductsByDynamicErrorTest: Input 값(Price)이 유효하지 않을 경우: 400 Bad Request")
    void searchProductsByDynamicErrorTest_InvalidPriceInput() throws Exception {
        // given
        BigDecimal invalidMinPrice = new BigDecimal("100.00");
        BigDecimal invalidMaxPrice = new BigDecimal("50.00");

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search/dynamic")
                        .header("Content-Type", "application/json")
                        .param("minPrice", String.valueOf(invalidMinPrice))
                        .param("maxPrice", String.valueOf(invalidMaxPrice)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("The minPrice can't be greater then maxPrice!"));
    }

    @Test
    @DisplayName("정상 searchProductsByDynamicErrorTest: Input 값(ProductCategory)가 유효하지 않을 경우: 400 Bad Request")
    void searchProductsByDynamicErrorTest_InvalidProductCategoryInput() throws Exception {
        // given
        String invalidCategory = "INVALID_CATEGORY";

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search/dynamic")
                        .param("productCategoryList", invalidCategory)
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                .value("Invalid product categories: INVALID_CATEGORY"));
    }

    @Test
    @DisplayName("정상 searchProductsByDynamicErrorTest: Products 가 존재하지 않는 경우: 404 NotFound")
    void searchProductsByDynamicErrorTest_ProductNotFoundException() throws Exception {
        // given
        String unknownProductName = "unknownProductName";
        when(productQueryService.searchProducts(any(DynamicSearchProductQuery.class)))
                .thenThrow(new ProductNotFoundException("Could not find any products"));

        // when and then
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search/dynamic")
                        .param("name", unknownProductName)
                        .header("Content-Type", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                .value("Could not find any products"));
    }
}
