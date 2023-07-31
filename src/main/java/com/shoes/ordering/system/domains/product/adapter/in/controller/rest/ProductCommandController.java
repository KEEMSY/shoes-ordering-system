package com.shoes.ordering.system.domains.product.adapter.in.controller.rest;

import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.ports.input.service.ProductApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/products", produces = "application/vnd.API.v1+json")
public class ProductCommandController {

    private final ProductApplicationService productApplicationService;

    public ProductCommandController(ProductApplicationService productApplicationService) {
        this.productApplicationService = productApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> createProduct(@RequestBody CreateProductCommand createProductCommand) {
        log.info("Creating product");
        CreateProductResponse createProductResponse = productApplicationService.createProduct(createProductCommand);
        log.info("Product created with Id: {}", createProductResponse.getProductId());
        return ResponseEntity.ok(createProductResponse);
    }

    @PostMapping("/{productId}")
    public ResponseEntity<UpdateProductResponse> updateProduct(@RequestBody UpdateProductCommand updateProductCommand) {
        UpdateProductResponse updateProductResponse = productApplicationService.updateProduct(updateProductCommand);
        log.info("Product updated with id: {}", updateProductResponse.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(updateProductResponse);
    }
}
