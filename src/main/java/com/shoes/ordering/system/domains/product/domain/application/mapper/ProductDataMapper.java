package com.shoes.ordering.system.domains.product.domain.application.mapper;

import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductResponse;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDataMapper {
    public Product creatProductCommandToProduct(CreateProductCommand createProductCommand) {
        return Product.builder()
                .name(createProductCommand.getName())
                .productCategory(createProductCommand.getProductCategory())
                .description(createProductCommand.getDescription())
                .price(createProductCommand.getPrice())
                .productImages(createProductCommand.getProductImages())
                .build();
    }

    public CreateProductResponse productToCreateProductResponse(Product product) {
        return CreateProductResponse.builder()
                .name(product.getName())
                .productCategory(product.getProductCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .productImages(product.getProductImages())
                .build();
    }
}
