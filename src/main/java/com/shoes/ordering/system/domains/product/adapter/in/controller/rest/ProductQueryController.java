package com.shoes.ordering.system.domains.product.adapter.in.controller.rest;

import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.ports.input.service.ProductQueryService;
import com.shoes.ordering.system.domains.product.domain.core.exception.ProductDomainException;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
}
