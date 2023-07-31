package com.shoes.ordering.system.domains.product.adapter.in.controller.rest;

import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductQuery;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.ports.input.service.ProductQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
