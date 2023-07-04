package com.shoes.ordering.system.domains.product.domain.application.mapper;

import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductResponse;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public Product updateProductCommandToProduct(UpdateProductCommand updateProductCommand) {
        return Product.builder()
                .productId(new ProductId(updateProductCommand.getProductId()))
                .name(updateProductCommand.getName())
                .description(updateProductCommand.getDescription())
                .productCategory(updateProductCommand.getProductCategory())
                .price(updateProductCommand.getPrice())
                .productImages(updateProductCommand.getProductImages())
                .build();
    }

    public UpdateProductResponse productToUpdateProductResponse(Product product) {
        return UpdateProductResponse.builder()
                .productId(product.getId().getValue())
                .name(product.getName())
                .productCategory(product.getProductCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .productImages(product.getProductImages())
                .build();
    }

    public TrackProductResponse productToTrackProductResponse(Product product) {
        return TrackProductResponse.builder()
                .productId(product.getId().getValue())
                .name(product.getName())
                .productCategory(product.getProductCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .productImages(product.getProductImages())
                .build();
    }

    public TrackProductListResponse productListToTrackProductListResponse(List<Product> products) {
        return TrackProductListResponse.builder()
                .productList(products)
                .build();
    }
}
