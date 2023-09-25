package com.shoes.ordering.system.domains.product.adapter.in.controller.rest;

import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.*;
import com.shoes.ordering.system.domains.product.domain.application.ports.input.service.ProductQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/products", produces = "application/vnd.API.v1+json")
public class ProductQueryController {
    private final ProductQueryService productQueryService;

    public ProductQueryController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<TrackProductResponse> getProductByProductId(@PathVariable UUID productId) {
        TrackProductResponse trackProductResponse
                = productQueryService.trackProduct(TrackProductQuery.builder().productId(productId).build());

        log.info("Return product with ProductId: {}", trackProductResponse.getProductId());
        return ResponseEntity.ok(trackProductResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<TrackProductListResponse> searchProductsByCategories(@RequestParam List<String> trackProperties) {
        TrackProductListQuery trackProductListQuery = TrackProductListQuery.builder()
                .productCategoryList(trackProperties)
                .build();

        TrackProductListResponse trackProductListResponse
                = productQueryService.trackProductWithCategory(trackProductListQuery);
        log.info("Return product with ProductCategories: {}", trackProductListQuery.getProductCategoryList());
        return ResponseEntity.ok(trackProductListResponse);
    }

    @GetMapping("/search/dynamic")
    public ResponseEntity<TrackProductListResponse> searchProductsByDynamic(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "productCategoryList", required = false) List<String> productCategoryList,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice
    ) {
        DynamicSearchProductQuery.Builder queryBuilder = DynamicSearchProductQuery.builder()
                .name(name)
                .productCategoryList(productCategoryList);

        if (minPrice != null) {
            queryBuilder.minPrice(new Money(minPrice));
        }

        if (maxPrice != null) {
            queryBuilder.maxPrice(new Money(maxPrice));
        }

        DynamicSearchProductQuery dynamicSearchProductQuery = queryBuilder.build();
        TrackProductListResponse trackProductListResponse
                = productQueryService.searchProducts(dynamicSearchProductQuery);
        log.info("Return product with Name: {}, ProductCategories: {}, minPrice: {}, maxPrice: {}"
                ,dynamicSearchProductQuery.getName()
                , dynamicSearchProductQuery.getProductCategoryList()
                ,dynamicSearchProductQuery.getMinPrice()
                , dynamicSearchProductQuery.getMaxPrice());

        return ResponseEntity.ok(trackProductListResponse);
    }
}
