package com.shoes.ordering.system.domains.product.domain.application.mapper;

import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.create.CreateProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductListResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.track.TrackProductResponse;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductCommand;
import com.shoes.ordering.system.domains.product.domain.application.dto.update.UpdateProductResponse;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.entity.ProductImage;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductImageId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductDataMapper {
    public Product creatProductCommandToProduct(CreateProductCommand createProductCommand) {
        return Product.builder()
                .name(createProductCommand.getName())
                .productCategory(createProductCommand.getProductCategory())
                .description(createProductCommand.getDescription())
                .price(createProductCommand.getPrice())
//                .productImages(productImageStringsToProductImages(createProductCommand.getProductImages()))
                .build();
    }

//    private List<ProductImage> productImageStringsToProductImages(List<String> productImages) {
//        return productImages.stream()
//                .map(productImageUrl -> ProductImage.builder()
//                        .productImageId(new ProductImageId(UUID.randomUUID()))
//                        .productImageUrl(productImageUrl)
//                        .build())
//                .collect(Collectors.toList());
//    }

    public CreateProductResponse productToCreateProductResponse(Product product) {
        return CreateProductResponse.builder()
                .name(product.getName())
                .productCategory(product.getProductCategory())
                .description(product.getDescription())
                .price(product.getPrice())
//                .productImages(productImagesToString(product.getProductImages()))
                .build();
    }

//    private List<String> productImagesToString(List<ProductImage> productImages) {
//        return productImages.stream()
//            .map(ProductImage::getProductImageUrl)
//            .collect(Collectors.toList());
//    }

    public Product updateProductCommandToProduct(UpdateProductCommand updateProductCommand) {
        return Product.builder()
                .productId(new ProductId(updateProductCommand.getProductId()))
                .name(updateProductCommand.getName())
                .description(updateProductCommand.getDescription())
                .productCategory(updateProductCommand.getProductCategory())
                .price(updateProductCommand.getPrice())
//                .productImages(updateProductCommand.getProductImages())
                .build();
    }

    public UpdateProductResponse productToUpdateProductResponse(Product product) {
        return UpdateProductResponse.builder()
                .productId(product.getId().getValue())
                .name(product.getName())
                .productCategory(product.getProductCategory())
                .description(product.getDescription())
                .price(product.getPrice())
//                .productImages(productImagesToString(product.getProductImages()))
                .build();
    }

    public TrackProductResponse productToTrackProductResponse(Product product) {
        return TrackProductResponse.builder()
                .productId(product.getId().getValue())
                .name(product.getName())
                .productCategory(product.getProductCategory())
                .description(product.getDescription())
                .price(product.getPrice())
//                .productImages(productImagesToString(product.getProductImages()))
                .build();
    }

    public TrackProductListResponse productListToTrackProductListResponse(List<Product> products) {
        return TrackProductListResponse.builder()
                .productList(products)
                .build();
    }
}
