package com.shoes.ordering.system.domains.product.adapter.out.dataaccess.mapper;


import com.shoes.ordering.system.domains.common.valueobject.Money;
import com.shoes.ordering.system.domains.product.adapter.out.dataaccess.entity.ProductEntity;
import com.shoes.ordering.system.domains.product.domain.core.entity.Product;
import com.shoes.ordering.system.domains.product.domain.core.valueobject.ProductId;
import org.springframework.stereotype.Component;



@Component
public class ProductDataAccessMapper {

    public ProductEntity productToProductEntity(Product product) {
       return  ProductEntity.builder()
                .productId(product.getId().getValue())
                .name(product.getName())
                .description(product.getDescription())
               .price(product.getPrice().getAmount())
                .productCategory(product.getProductCategory())
//                .productImages(productImagesToProductImageEntities(product.getProductImages()))
                .build();
    }

    public Product productEntityToProduct(ProductEntity productEntity) {
        return Product.builder()
                .productId(new ProductId(productEntity.getProductId()))
                .name(productEntity.getName())
                .price(new Money(productEntity.getPrice()))
                .description(productEntity.getDescription())
                .productCategory(productEntity.getProductCategory())
//                .productImages(productImageEntitiesToProductImages(productEntity.getProductImages()))
                .build();
    }

//    private List<ProductImageEntity> productImagesToProductImageEntities(List<ProductImage> productImageUrls) {
//        return productImageUrls.stream()
//                .map(productImageUrl -> ProductImageEntity.builder()
//                        .productImageId(productImageUrl.getProductImageId().getValue())
//                        .productImageUrl(productImageUrl.getProductImageUrl())
//                        .build())
//                .collect(Collectors.toList());
//    }


//    private List<ProductImage> productImageEntitiesToProductImages(List<ProductImageEntity> productImages) {
//        return productImages.stream()
//                .map(productImage -> ProductImage.builder()
//                        .productId(new ProductId(productImage.getProduct().getProductId()))
//                        .productImageId(new ProductImageId(productImage.getProductImageId()))
//                        .productImageUrl(productImage.getProductImageUrl())
//                        .build())
//                .collect(Collectors.toList());
//    }
}
